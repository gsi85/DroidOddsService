package com.droidodds.dao.repository;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.Set;

/**
 * @author Laszlo_Sisa
 */
public interface OddsRepository {

    Odds getOdds(final Set<Card> cardsInHand);

    void persistOdds(final Set<Card> cardsInHand, final Odds odds);

}
