package com.droidodds.service;

import com.droidodds.dao.OddsDao;
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
public class DaoLoadingOddsCalculatorService implements OddsCalculatorService {

    @Autowired
    private OddsDao oddsDao;

    @Autowired
    private OddsCalculator multiThreadedFlopCombinationsDistributor;

    @Autowired
    private CardsInputValidator cardsInputValidator;

    @Override
    public Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        Set<Card> onDeck = getNullSafeDeck(cardsOnDeck);
        validateInput(cardsInHand, onDeck);
        return getOdds(cardsInHand, onDeck);
    }

    private void validateInput(final Set<Card> cardsInHand, final Set<Card> onDeck) {
        cardsInputValidator.validateInput(cardsInHand, onDeck);
    }

    private Set<Card> getNullSafeDeck(final Set<Card> cardsOnDeck) {
        return cardsOnDeck != null ? cardsOnDeck : Collections.emptySet();
    }

    private Odds getOdds(final Set<Card> cardsInHand, final Set<Card> onDeck) {
        return persistRequired(onDeck) ? getOrLoadDao(cardsInHand, onDeck) : doCalculate(cardsInHand, onDeck);
    }

    private boolean persistRequired(final Set<Card> onDeck) {
        return onDeck.isEmpty();
    }

    private Odds getOrLoadDao(final Set<Card> cardsInHand, final Set<Card> onDeck) {
        Odds odds = oddsDao.getOdds(cardsInHand);
        if (odds == null) {
            odds = doCalculate(cardsInHand, onDeck);
            oddsDao.persistOdds(cardsInHand, odds);
        }
        return odds;
    }

    private Odds doCalculate(final Set<Card> cardsInHand, final Set<Card> onDeck) {
        return multiThreadedFlopCombinationsDistributor.calculateOdds(cardsInHand, onDeck);
    }
}
