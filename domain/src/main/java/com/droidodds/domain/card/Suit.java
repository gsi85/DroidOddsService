package com.droidodds.domain.card;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of available card suits.
 *
 * @author Laszlo Sisa
 */
public enum Suit {
    SPADES("spades", "S"), HEARTS("hearts", "H"), DIAMONDS("diamonds", "D"), CLUBS("clubs", "C");

    private static final Map<String, Suit> SHORT_NAME_MAP;

    static {
        SHORT_NAME_MAP = new HashMap<>();
        Arrays.stream(Suit.values()).forEach(suit -> SHORT_NAME_MAP.put(suit.getShortName(), suit));
    }

    private final String name;
    private final String shortName;

    private Suit(final String name, final String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public static Suit getByShortName(final String shortName) {
        return SHORT_NAME_MAP.get(shortName);
    }

}
