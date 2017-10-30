package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class MultiThreadedOddsCalculator implements OddsCalculator {

    @Override
    public Odds calculateOdds(final List<Card> cardsToEvaluate) {

        List<List<Card>> combinationsForRemaining = new ArrayList<>();
        List<Card> availableCards = CompleteDeck.getCompleteDeck().stream().filter(card -> !cardsToEvaluate.contains(card)).collect(Collectors.toList());

        fillUpCombinationsList(combinationsForRemaining, availableCards, new Stack<>(), 9 - cardsToEvaluate.size());


        return new Odds(1, 2, 3);
    }

    private void fillUpCombinationsList(final List<List<Card>> combinationsForRemaining, final List<Card> availableCards, final Stack<Card> currentSubset, final int requiredSize) {
        for (Card currentCard : availableCards) {
            currentSubset.push(currentCard);
            if(currentSubset.size() == requiredSize) {
                combinationsForRemaining.add(new ArrayList<>(currentSubset));
            } else {
                List<Card> nextAvailCards = new ArrayList<>(availableCards);
                nextAvailCards.remove(currentCard);
                fillUpCombinationsList(combinationsForRemaining, nextAvailCards, currentSubset, requiredSize);
            }
            currentSubset.pop();
        }
    }


}
