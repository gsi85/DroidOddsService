package com.droidodds.engine.evaluator;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import com.droidodds.domain.hand.Hand;
import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import com.google.common.collect.EnumMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class FivCardsEvaluator implements HandEvaluator {

    private final Ordering<Multiset.Entry<Rank>> byCountThenRank = new CountThenRankOrdering();

    @Override
    public EvaluatedHand evaluate(List<Card> cards) {
        Hand hand;
        final Set<Suit> suits = EnumSet.noneOf(Suit.class);
        final Multiset<Rank> ranks = EnumMultiset.create(Rank.class);

        preProcess(cards, suits, ranks);
        LinkedList<Rank> ascendingSortedDistinctRanks = sortRanks(ranks);

        final Rank first = ascendingSortedDistinctRanks.getFirst();
        final int distinctCount = ascendingSortedDistinctRanks.size();
        if (distinctCount == 5) {
            hand = evaluateFiveDistinctCards(suits, ascendingSortedDistinctRanks, first);
        } else if (distinctCount == 4) {
            hand = Hand.ONE_PAIR;
        } else if (distinctCount == 3) {
            hand = evaluateThreeDistinctCount(ranks, first);
        } else {
            hand = evaluateTwoDistinctCount(ranks, first);
        }

        return new EvaluatedHand(hand, ascendingSortedDistinctRanks);
    }

    private Hand evaluateFiveDistinctCards(final Set<Suit> suits, final LinkedList<Rank> ascendingSortedDistinctRanks, final Rank first) {
        Hand hand;
        final boolean flush = suits.size() == 1;
        if (first.ordinal() - ascendingSortedDistinctRanks.getLast().ordinal() == 4) {
            hand = flush ? Hand.STRAIGHT_FLUSH : Hand.STRAIGHT;
            if (hand == Hand.STRAIGHT_FLUSH && first == Rank.ACE) {
                hand = Hand.ROYAL_FLUSH;
            }
        } else if (first == Rank.ACE && ascendingSortedDistinctRanks.get(1) == Rank.FIVE) {
            hand = flush ? Hand.STRAIGHT_FLUSH : Hand.STRAIGHT;
            ascendingSortedDistinctRanks.addLast(ascendingSortedDistinctRanks.removeFirst());
        } else {
            hand = flush ? Hand.FLUSH : Hand.HIGH_CARD;
        }
        return hand;
    }

    private Hand evaluateThreeDistinctCount(final Multiset<Rank> ranks, final Rank first) {
        return ranks.count(first) == 2 ? Hand.TWO_PAIR : Hand.THREE_OF_A_KIND;
    }

    private Hand evaluateTwoDistinctCount(final Multiset<Rank> ranks, final Rank first) {
        return ranks.count(first) == 3 ? Hand.FULL_HOUSE : Hand.FOUR_OF_A_KIND;
    }

    private void preProcess(final List<Card> cards, final Set<Suit> suits, final Multiset<Rank> ranks) {
        cards.forEach(card -> {
            suits.add(card.getSuit());
            ranks.add(card.getRank());
        });
    }

    private LinkedList<Rank> sortRanks(final Multiset<Rank> ranks) {
        final LinkedList<Rank> ascendingSortedDistinctRanks = new LinkedList<>();
        for (final Multiset.Entry<Rank> entry : byCountThenRank.immutableSortedCopy(ranks.entrySet())) {
            ascendingSortedDistinctRanks.addFirst(entry.getElement());
        }
        return ascendingSortedDistinctRanks;
    }

    private class CountThenRankOrdering extends Ordering<Multiset.Entry<Rank>> {

        @Override
        public int compare(final Multiset.Entry<Rank> rankBag1, final Multiset.Entry<Rank> rankBag2) {
            return rankBag1.getCount() > rankBag2.getCount() ? 1 : rankBag1.getCount() < rankBag2.getCount() ? -1 : rankBag1.getElement()
                    .getValue() > rankBag2.getElement().getValue() ? 1 : rankBag1.getElement().getValue() > rankBag2.getElement()
                    .getValue() ? -1 : 0;
        }
    }

}
