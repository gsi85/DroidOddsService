package com.droidodds.service;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.Set;

/**
 * @author Laszlo_Sisa
 */
public interface OddsCalculatorService {

    Odds calculateOdds(Set<Card> cardsInHand, Set<Card> cardsOnDeck);

}
