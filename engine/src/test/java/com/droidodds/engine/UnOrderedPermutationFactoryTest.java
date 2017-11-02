package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Laszlo_Sisa
 */
@RunWith(Parameterized.class)
public class UnOrderedPermutationFactoryTest {

    private final List<Card> availableCards;
    private final int requiredLength;
    private final int expectedSize;
    private UnOrderedPermutationFactory underTest;

    public UnOrderedPermutationFactoryTest(final List<Card> availableCards, final int requiredLength, final int expectedSize) {
        this.availableCards = availableCards;
        this.requiredLength = requiredLength;
        this.expectedSize = expectedSize;
    }

    @Before
    public void init() {
        underTest = new UnOrderedPermutationFactory();
    }

    @Test
    public void test() {
        List<Set<Card>> actual = underTest.getUnOrderedPermutationWithoutRepetition(availableCards, requiredLength);

        Assert.assertEquals(expectedSize, actual.size());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> provideTestData() {
        return Arrays.asList(new Object[][]{
                {CompleteDeck.getCompleteDeck(), 1, 52},
                {CompleteDeck.getCompleteDeck(), 2, 1326},
                {CompleteDeck.getCompleteDeck(), 3, 22100},
                {CompleteDeck.getCompleteDeck(), 4, 270725},
                {CompleteDeck.getCompleteDeck(), 5, 2598960},
                {getListOf45(), 2, 990}
        });
    }

    private static Object getListOf45() {
        List<Card> completeDeck = new ArrayList<>(CompleteDeck.getCompleteDeck());
        completeDeck.remove(new Card(Rank.FOUR, Suit.HEARTS));
        completeDeck.remove(new Card(Rank.JACK, Suit.CLUBS));
        completeDeck.remove(new Card(Rank.KING, Suit.CLUBS));
        completeDeck.remove(new Card(Rank.KING, Suit.SPADES));
        completeDeck.remove(new Card(Rank.KING, Suit.HEARTS));
        completeDeck.remove(new Card(Rank.DEUCE, Suit.SPADES));
        completeDeck.remove(new Card(Rank.THREE, Suit.CLUBS));
        return completeDeck;
    }

}
