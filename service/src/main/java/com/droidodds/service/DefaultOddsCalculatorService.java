package com.droidodds.service;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.OddsCalculator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class DefaultOddsCalculatorService implements OddsCalculatorService {

    @Autowired
    private OddsCalculator oddsCalculator;

    @Override
    public Odds calculateOdds(final List<Card> cardsToEvaluate) {
        return oddsCalculator.calculateOdds(cardsToEvaluate);
    }
}
