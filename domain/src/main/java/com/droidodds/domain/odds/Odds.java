package com.droidodds.domain.odds;

/**
 * Domain object representing gaming odds.
 *
 * @author Laszlo Sisa
 *
 */
public class Odds {

    private final long winCount;
    private final long splitCount;
    private final long totalDealCount;

    public Odds(final long winCount, final long splitCount, final long totalDealCount) {
        this.winCount = winCount;
        this.splitCount = splitCount;
        this.totalDealCount = totalDealCount;
    }

    public long getWinCount() {
        return winCount;
    }

    public long getSplitCount() {
        return splitCount;
    }

    public long getTotalDealCount() {
        return totalDealCount;
    }

    @Override
    public String toString() {
        return String.format("Odds [winCount=%s, splitCount=%s, totalDealCount=%s]", winCount, splitCount, totalDealCount);
    }

}
