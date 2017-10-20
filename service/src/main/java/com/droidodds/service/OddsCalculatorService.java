package com.droidodds.service;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.List;

/**
 * @author Laszlo_Sisa
 */
public interface OddsCalculatorService {

    Odds calculateOdds(List<Card> cardsToEvaluate);

}
