package com.droidodds.application.cache;

import com.droidodds.application.loader.TwoKnownCardsDbLoader;
import com.droidodds.domain.card.Card;
import com.droidodds.engine.UnOrderedPermutationFactory;
import com.droidodds.engine.evaluator.HandEvaluator;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class FiveCardCacheLoader implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FiveCardCacheLoader.class);

    @Autowired
    private HandEvaluator fivCardsEvaluator;
    @Autowired
    private UnOrderedPermutationFactory<Card> unOrderedPermutationFactory;
    @Autowired
    private TwoKnownCardsDbLoader twoKnownCardsDbLoader;

    @Override
    public void run(final String... strings) throws Exception {
        loadFiveCardCache();
        twoKnownCardsDbLoader.loadTwoKnownCardsDb();
    }

    private void loadFiveCardCache() {
        LOGGER.info("Warming up five card cache...");
        unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(CompleteDeck.getCompleteDeck(), 5).forEach(this::loadVariationToCache);
        LOGGER.info("Five card cache fully loaded.");
    }

    private void loadVariationToCache(Set<Card> variation) {
        List<Card> cards = new ArrayList<>(variation);
        Collections.sort(cards);
        fivCardsEvaluator.evaluate(cards);
    }
}
