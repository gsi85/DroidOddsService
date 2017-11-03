package com.droidodds.validator;

import com.droidodds.domain.card.Card;
import com.droidodds.exception.InvalidCardsInputException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class CardsInputValidator {

    private static final List<Integer> ACCEPTED_CARDS_ON_DECK_SIZES = Arrays.asList(0, 3, 4, 5);
    private static final int ACCEPTED_CARDS_IN_HAND_SIZE = 2;

    public void validateInput(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        if (cardsInHand.size() != ACCEPTED_CARDS_IN_HAND_SIZE || !ACCEPTED_CARDS_ON_DECK_SIZES.contains(cardsOnDeck.size())) {
            throw new InvalidCardsInputException("Invalid input, in hand: " + cardsInHand.toString() + ", on deck: " + cardsOnDeck.toString());
        }
    }

}
