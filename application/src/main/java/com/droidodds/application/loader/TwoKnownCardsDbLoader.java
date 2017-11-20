package com.droidodds.application.loader;

import com.droidodds.domain.card.Card;
import com.droidodds.engine.UnOrderedPermutationFactory;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import com.droidodds.loader.TwoKnownCardsDbLoaderStatus;
import com.droidodds.service.OddsCalculatorService;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class TwoKnownCardsDbLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwoKnownCardsDbLoader.class);

    @Autowired
    private OddsCalculatorService oddsCalculatorService;
    @Autowired
    private TwoKnownCardsDbLoaderStatus twoKnownCardsDbLoaderStatus;
    @Autowired
    private UnOrderedPermutationFactory<Card> unOrderedPermutationFactory;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void loadTwoKnownCardsDb() {
        executor.execute(this::loadDb);
    }

    private void loadDb() {
        unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(CompleteDeck.getCompleteDeck(), 2).forEach(this::doLoad);
    }

    private void doLoad(final Set<Card> cards) {
        LOGGER.info("Calculating odds for: {}", cards);
        twoKnownCardsDbLoaderStatus.setInProgressHand(cards);
        oddsCalculatorService.calculateOdds(cards, null);
        twoKnownCardsDbLoaderStatus.incrementCalculatedCounter();
    }

}
