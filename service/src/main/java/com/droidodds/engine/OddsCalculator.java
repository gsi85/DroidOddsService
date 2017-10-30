package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.Set;

/**
 * @author Laszlo_Sisa
 */
public interface OddsCalculator {

    Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck);

}
