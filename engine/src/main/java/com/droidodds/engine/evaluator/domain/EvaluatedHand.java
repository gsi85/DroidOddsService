package com.droidodds.engine.evaluator.domain;

import com.droidodds.domain.card.Rank;
import com.droidodds.domain.hand.Hand;
import java.util.List;

/**
 * @author Laszlo_Sisa
 */
public class EvaluatedHand implements Comparable<EvaluatedHand> {

    private final Hand hand;
    private final List<Rank> ascendingSortedDistinctRanks;

    public EvaluatedHand(final Hand hand, final List<Rank> ascendingSortedDistinctRanks) {
        this.hand = hand;
        this.ascendingSortedDistinctRanks = ascendingSortedDistinctRanks;
    }

    public Hand getHand() {
        return hand;
    }

    public List<Rank> getAscendingSortedDistinctRanks() {
        return ascendingSortedDistinctRanks;
    }

    @Override
    public int compareTo(final EvaluatedHand another) {
        int result = 0;
        if (hand.getValue() > another.getHand().getValue()) {
            result = 1;
        } else if (hand.getValue() < another.getHand().getValue()) {
            result = -1;
        } else {
            for (int i = 0; i < ascendingSortedDistinctRanks.size() && i < another.getAscendingSortedDistinctRanks().size() && result == 0; i++) {
                result = ascendingSortedDistinctRanks.get(i).compareTo(another.getAscendingSortedDistinctRanks().get(i));
            }
        }

        return result;
    }
}
