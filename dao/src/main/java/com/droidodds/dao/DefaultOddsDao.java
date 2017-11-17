package com.droidodds.dao;

import com.droidodds.dao.repository.OddsRepository;
import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class DefaultOddsDao implements OddsDao {

    @Autowired
    private OddsRepository oddsRepository;

    @Override
    public Odds getOdds(final Set<Card> cardsInHand) {
        return oddsRepository.getOdds(cardsInHand);
    }

    @Override
    public void persistOdds(final Set<Card> cardsInHand, final Odds odds) {
        oddsRepository.persistOdds(cardsInHand, odds);
    }
}
