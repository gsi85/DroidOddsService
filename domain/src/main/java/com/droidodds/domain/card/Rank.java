package com.droidodds.domain.card;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of available card ranks.
 *
 * @author Laszlo Sisa
 */
public enum Rank {
    DEUCE(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), JACK(
            11, "J"), QUEEN(12, "Q"), KING(13, "K"), ACE(14, "A");

    private static final Map<String, Rank> SHORT_NAME_MAP;

    static {
        SHORT_NAME_MAP = new HashMap<>();
        Arrays.stream(Rank.values()).forEach(rank -> SHORT_NAME_MAP.put(rank.getShortName(), rank));
    }

    private final int value;
    private final String shortName;

    Rank(final int value, final String shortName) {
        this.value = value;
        this.shortName = shortName;
    }

    public int getValue() {
        return value;
    }

    public String getShortName() {
        return shortName;
    }

    public static Rank getByShortName(final String shortName) {
        return SHORT_NAME_MAP.get(shortName);
    }
}

