package com.droidodds.engine.evaluator;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.hand.Hand;
import java.util.List;

/**
 * @author Laszlo_Sisa
 */
public interface HandEvaluator {

    Hand evaluate(List<Card> cards);

}
