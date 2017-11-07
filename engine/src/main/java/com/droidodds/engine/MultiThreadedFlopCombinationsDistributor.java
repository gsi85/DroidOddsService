package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class MultiThreadedFlopCombinationsDistributor implements OddsCalculator {

    private static final int FLOP_CARD_SIZE = 3;

    @Autowired
    private OddsCalculator multiThreadedOddsCalculator;
    @Autowired
    private UnOrderedPermutationFactory unOrderedPermutationFactory;
    private Executor executor = new ForkJoinPool();

    @Override
    public Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        return isFlopKnown(cardsOnDeck) ? multiThreadedOddsCalculator.calculateOdds(cardsInHand, cardsOnDeck) : distributeTasks(cardsInHand);
    }

    private boolean isFlopKnown(final Set<Card> cardsOnDeck) {
        return cardsOnDeck.size() >= FLOP_CARD_SIZE;
    }

    private Odds distributeTasks(final Set<Card> cardsInHand) {
        List<Card> availableCards = CompleteDeck.getCompleteDeck().stream().filter(card -> !cardsInHand.contains(card)).collect(Collectors.toList());
        List<Set<Card>> flopCombinations = unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(availableCards, FLOP_CARD_SIZE);
        CompletableFuture<List<Odds>> joinedTasks = sequence(createTasks(cardsInHand, flopCombinations));
        return combineOdds(joinedTasks);
    }

    private Odds combineOdds(final CompletableFuture<List<Odds>> joinedTasks) {
        int winCount = 0;
        int splitCount = 0;
        int totalDealCount = 0;
        List<Odds> joinedOdds = joinedTasks.join();
        int oddSize = joinedOdds.size();
        for (Odds odds : joinedOdds) {
            winCount += odds.getWinCount() / oddSize;
            splitCount += odds.getSplitCount() / oddSize;
            totalDealCount += odds.getTotalDealCount() / oddSize;
        }
        return new Odds(winCount, splitCount, totalDealCount);
    }

    private List<CompletableFuture<Odds>> createTasks(final Set<Card> cardsInHand, final List<Set<Card>> flopCombinations) {
        return flopCombinations.stream().map(cardsOnDeck -> calculateOddsFuture(cardsInHand, cardsOnDeck)).collect(Collectors.toList());
    }

    private CompletableFuture<Odds> calculateOddsFuture(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        return CompletableFuture.supplyAsync(() -> multiThreadedOddsCalculator.calculateOdds(cardsInHand, cardsOnDeck), executor);
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList())
        );
    }
}
