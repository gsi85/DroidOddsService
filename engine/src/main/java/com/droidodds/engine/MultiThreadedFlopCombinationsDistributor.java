package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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
    private UnOrderedPermutationFactory<Card> unOrderedPermutationFactory;
    private Executor executor = Executors.newSingleThreadExecutor();

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

    private List<CompletableFuture<Odds>> createTasks(final Set<Card> cardsInHand, final List<Set<Card>> flopCombinations) {
        return flopCombinations.stream().map(cardsOnDeck -> calculateOddsFuture(cardsInHand, cardsOnDeck)).collect(Collectors.toList());
    }

    private CompletableFuture<Odds> calculateOddsFuture(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        return CompletableFuture.supplyAsync(() -> multiThreadedOddsCalculator.calculateOdds(cardsInHand, cardsOnDeck), executor);
    }

    private Odds combineOdds(final CompletableFuture<List<Odds>> joinedTasks) {
        long winCount = 0;
        long splitCount = 0;
        long totalDealCount = 0;
        for (Odds odds : joinedTasks.join()) {
            winCount += odds.getWinCount();
            splitCount += odds.getSplitCount();
            totalDealCount += odds.getTotalDealCount();
        }
        return new Odds(winCount, splitCount, totalDealCount);
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
