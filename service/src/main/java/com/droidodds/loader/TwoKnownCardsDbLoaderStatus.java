package com.droidodds.loader;

import com.droidodds.domain.card.Card;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class TwoKnownCardsDbLoaderStatus {

    private AtomicInteger calculatedCounter;
    private AtomicReference<Set<Card>> inProgressHand;

    public TwoKnownCardsDbLoaderStatus() {
        calculatedCounter = new AtomicInteger(0);
        inProgressHand = new AtomicReference<>();
    }

    public int getCalculatedCounter() {
        return calculatedCounter.get();
    }

    public void incrementCalculatedCounter() {
        calculatedCounter.incrementAndGet();
    }

    public Set<Card> getInProgressHand() {
        return inProgressHand.get();
    }

    public void setInProgressHand(final Set<Card> inProgress) {
        inProgressHand.set(inProgress);
    }
}
