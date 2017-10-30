package com.droidodds.engine.evaluator;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.hand.Hand;
import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class SevenCardsEvaluator implements HandEvaluator {

    private static final EvaluatedHand LOWEST_POSSIBLE_HAND = new EvaluatedHand(Hand.HIGH_CARD, Arrays.asList(Rank.SEVEN, Rank.FIVE,
            Rank.FOUR, Rank.THREE, Rank.DEUCE));

    @Autowired
    private HandEvaluator fivCardsEvaluator;

    @Override
    public EvaluatedHand evaluate(final List<Card> cards) {
        EvaluatedHand bestHand = LOWEST_POSSIBLE_HAND;

        for (int firstOutCardIndex = 0; firstOutCardIndex < cards.size() - 1; firstOutCardIndex++) {
            for (int secondCardOutIndex = firstOutCardIndex + 1; secondCardOutIndex < cards.size(); secondCardOutIndex++) {
                List<Card> cardsToEvaluate = getCardsToEvaluate(cards, cards.get(firstOutCardIndex), cards.get(secondCardOutIndex));
                EvaluatedHand currentHand = fivCardsEvaluator.evaluate(cardsToEvaluate);
                bestHand = currentHand.compareTo(bestHand) > 0 ? currentHand : bestHand;
            }
        }


        return bestHand;
    }

    private List<Card> getCardsToEvaluate(final List<Card> cards, final Card firstOutCard, final Card secondOutCard) {
        return cards.stream().filter(card -> !card.equals(firstOutCard) && !card.equals(secondOutCard)).collect(Collectors.toList());
    }

}
