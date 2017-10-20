package com.droidodds.domain.card;

/**
 * Domain objects describing a playing card.
 *
 * @author Laszlo Sisa
 */
public class Card implements Comparable<Card> {

    private final Rank rank;
    private final Suit suit;

    /**
     * Default constructor of a playing card.
     *
     * @param rank the {@link Rank} of the card
     * @param suit the {@link Suit} of the card
     */
    public Card(final Rank rank, final Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getAbbreviatedName() {
        return String.format("%s%s", suit.getShortName(), rank.getShortName());
    }

    @Override
    public String toString() {
        return String.format("%s%s", suit.getShortName(), rank.getShortName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Card other = (Card) obj;
        return rank == other.rank && suit == other.suit;
    }

    @Override
    public int compareTo(final Card another) {
        int compare;
        if (rank.getValue() > another.getRank().getValue())
            compare = 1;
        else if (rank.getValue() < another.getRank().getValue())
            compare = -1;
        else
            compare = suit.compareTo(another.getSuit());
        return compare;
    }

}
