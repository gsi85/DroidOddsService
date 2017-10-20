package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class MultiThreadedOddsCalculator implements OddsCalculator {

    @Override
    public Odds calculateOdds(final List<Card> cardsToEvaluate) {
        return new Odds(1, 2, 3);
    }
}
