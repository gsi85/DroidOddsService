package com.droidodds.service;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.OddsCalculator;
import com.droidodds.validator.CardsInputValidator;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class DefaultOddsCalculatorService implements OddsCalculatorService {

    @Autowired
    private OddsCalculator oddsCalculator;

    @Autowired
    private CardsInputValidator cardsInputValidator;

    @Override
    public Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        Set<Card> onDeck = cardsOnDeck != null ? cardsOnDeck : Collections.emptySet();
        cardsInputValidator.validateInput(cardsInHand, onDeck);
        return oddsCalculator.calculateOdds(cardsInHand, onDeck);
    }
}
