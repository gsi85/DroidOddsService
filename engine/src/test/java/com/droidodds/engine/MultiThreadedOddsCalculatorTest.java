package com.droidodds.engine;

import static org.junit.Assert.*;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author Laszlo_Sisa
 */
public class MultiThreadedOddsCalculatorTest {

    @Test
    public void test() {
        MultiThreadedOddsCalculator multiThreadedOddsCalculator = new MultiThreadedOddsCalculator();
        multiThreadedOddsCalculator.calculateOdds(Arrays.asList(new Card(Rank.DEUCE, Suit.CLUBS), new Card(Rank.THREE, Suit.CLUBS), new Card(Rank.FOUR, Suit.CLUBS), new Card(Rank.FIVE, Suit.CLUBS), new Card(Rank.SIX, Suit.CLUBS)));

    }

}
