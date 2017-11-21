package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.HandEvaluator;
import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
class OddsCalculatorTask {

    private static final int OPPONENT_CARD_COUNT = 2;

    @Autowired
    private HandEvaluator sevenCardsEvaluator;
    @Autowired
    private UnOrderedPermutationFactory<Card> unOrderedPermutationFactory;
    private Executor executor = new ForkJoinPool();

    CompletableFuture<Odds> calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck, final Set<Card> cardsOnDeckCombinations, final List<Card> availableCards) {
        return CompletableFuture.supplyAsync(() -> getOdds(cardsInHand, cardsOnDeck, cardsOnDeckCombinations, availableCards), executor);
    }

    private Odds getOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck, final Set<Card> deckCombination, final List<Card> availableCards) {
        long winCount = 0;
        long splitCount = 0;
        long totalDealCount = 0;

        deckCombination.addAll(cardsOnDeck);
        EvaluatedHand playersHand = sevenCardsEvaluator.evaluate(Stream.concat(cardsInHand.stream(), deckCombination.stream()).sorted().collect(Collectors.toList()));

        List<Set<Card>> opponentCombinations = getOpponentCombinations(availableCards, deckCombination);
        for (Set<Card> opponentCards : opponentCombinations) {
            EvaluatedHand opponentHand = sevenCardsEvaluator.evaluate(Stream.concat(deckCombination.stream(), opponentCards.stream()).sorted().collect(Collectors.toList()));

            final int compare = playersHand.compareTo(opponentHand);
            if (compare > 0) {
                winCount++;
            } else if (compare == 0) {
                splitCount++;
            }
            totalDealCount++;
        }

        return new Odds(winCount, splitCount, totalDealCount);
    }

    private List<Set<Card>> getOpponentCombinations(final List<Card> availableCards, final Set<Card> deck) {
        List<Card> availCardsWODeck = new ArrayList<>(availableCards);
        availCardsWODeck.removeAll(deck);
        return unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(availCardsWODeck, OPPONENT_CARD_COUNT);
    }

}
