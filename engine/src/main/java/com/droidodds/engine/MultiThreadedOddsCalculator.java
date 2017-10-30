package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.SevenCardsEvaluator;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class MultiThreadedOddsCalculator implements OddsCalculator {

    private static final int FULL_DECK_SIZE = 5;
    private static final int OPONENT_CARD_COUNT = 2;

    @Autowired
    private UnOrderedPermutationFactory unOrderedPermutationFactory;
    @Autowired
    private SevenCardsEvaluator sevenCardsEvaluator;

    @Override
    public Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {

        List<Card> availableCards = CompleteDeck.getCompleteDeck().stream().filter(card -> !cardsInHand.contains(card)).collect(Collectors.toList());
        List<Set<Card>> cardsOnDeckCombinations = getCardsOnDeckCombinations(cardsOnDeck, availableCards);

        return getOdds(cardsInHand, cardsOnDeck, cardsOnDeckCombinations, availableCards);
    }

    private List<Set<Card>> getCardsOnDeckCombinations(final Set<Card> cardsOnDeck, final List<Card> availableCards) {
        int requiredLength = FULL_DECK_SIZE - cardsOnDeck.size();
        return requiredLength > 0 ? unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(availableCards, requiredLength) : new ArrayList<>(Collections.singletonList(cardsOnDeck));
    }

    private Odds getOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck, final List<Set<Card>> cardsOnDeckCombinations, final List<Card> availableCards) {
        int winCount = 0;
        int splitCount = 0;
        int totalDealCount = 0;

        for (Set<Card> deckCombination : cardsOnDeckCombinations) {
            deckCombination.addAll(cardsOnDeck);
            EvaluatedHand playersHand = sevenCardsEvaluator.evaluate(Stream.concat(cardsInHand.stream(), deckCombination.stream()).collect(Collectors.toList()));

            List<Set<Card>> opponentCombinations = getOpponentCombinations(availableCards, deckCombination);
            for (Set<Card> opponentCards : opponentCombinations) {
                EvaluatedHand opponentHand = sevenCardsEvaluator.evaluate(Stream.concat(deckCombination.stream(), opponentCards.stream()).collect(Collectors.toList()));

                final int compare = playersHand.compareTo(opponentHand);
                if (compare > 0) {
                    winCount++;
                } else if (compare == 0) {
                    splitCount++;
                }
                totalDealCount++;
            }
        }

        return new Odds(winCount, splitCount, totalDealCount);
    }

    private List<Set<Card>> getOpponentCombinations(final List<Card> availableCards, final Set<Card> deck) {
        List<Card> availCardsWODeck = new ArrayList<>(availableCards);
        availableCards.removeAll(deck);
        return unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(availCardsWODeck, OPONENT_CARD_COUNT);
    }


}
