package com.droidodds.web.controller.loader;

import com.droidodds.domain.card.Card;
import java.util.Set;

/**
 * @author Laszlo_Sisa
 */
public class TwoLoadStatus {

    private final int completed;
    private final Set<Card> currentlyInProgress;

    TwoLoadStatus(final int completed, final Set<Card> currentlyInProgress) {
        this.completed = completed;
        this.currentlyInProgress = currentlyInProgress;
    }

    public int getCompleted() {
        return completed;
    }

    public Set<Card> getCurrentlyInProgress() {
        return currentlyInProgress;
    }
}
