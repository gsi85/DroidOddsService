package com.droidodds.engine.evaluator.domain;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Laszlo_Sisa
 */
public class CompleteDeck {

    private static final List<Card> completeDeck;

    static {
        completeDeck = new ArrayList<Card>() {
            {
                for (final Rank rank : Rank.values()) {
                    for (final Suit suit : Suit.values()) {
                        add(new Card(rank, suit));
                    }
                }
            }
        };

    }

    private CompleteDeck() {

    }

    public static List<Card> getCompleteDeck() {
        return Collections.unmodifiableList(completeDeck);
    }

}
