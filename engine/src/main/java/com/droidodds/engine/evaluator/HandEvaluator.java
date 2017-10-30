package com.droidodds.engine.evaluator;

import com.droidodds.domain.card.Card;
import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import java.util.List;

/**
 * @author Laszlo_Sisa
 */
public interface HandEvaluator {

    EvaluatedHand evaluate(List<Card> cards);

}
