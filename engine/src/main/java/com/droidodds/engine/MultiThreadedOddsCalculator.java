package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.odds.Odds;
import com.droidodds.engine.evaluator.domain.CompleteDeck;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author Laszlo_Sisa
 */
@Service
public class MultiThreadedOddsCalculator implements OddsCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiThreadedOddsCalculator.class);

    @Autowired
    private UnOrderedPermutationFactory<Card> unOrderedPermutationFactory;
    @Autowired
    private OddsCalculatorTask oddsCalculatorTask;
    @Autowired
    private CacheManager cacheManager;

    private static final int FULL_DECK_SIZE = 5;

    @Override
    public Odds calculateOdds(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        LOGGER.info("Calculating odds for cards in hand: {}, cards on deck: {}", cardsInHand, cardsOnDeck);
        long startTime = System.currentTimeMillis();
        CompletableFuture<List<Odds>> joinedTasks = sequence(createTasks(cardsInHand, cardsOnDeck));
        Odds odds = combineOdds(joinedTasks);
        long estimatedTime = System.currentTimeMillis() - startTime;
        try {
            LOGGER.info("Done in: {} ms, seven card cache size: {}, five card cache size: {}", estimatedTime, estimatedCacheSize("sevenCardCache"), estimatedCacheSize("fiveCardCache"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return odds;
    }

    private int estimatedCacheSize(String cacheName) throws Exception {
        Object nativeCache = cacheManager.getCache(cacheName).getNativeCache();
        Field f = nativeCache.getClass().getDeclaredField("cache");
        f.setAccessible(true);
        return ((ConcurrentMap) f.get(nativeCache)).size();
    }

    private List<CompletableFuture<Odds>> createTasks(final Set<Card> cardsInHand, final Set<Card> cardsOnDeck) {
        List<Card> availableCards = CompleteDeck.getCompleteDeck().stream().filter(card -> !cardsInHand.contains(card) && !cardsOnDeck.contains(card)).collect(Collectors.toList());
        return getCardsOnDeckCombinations(cardsOnDeck, availableCards).stream().map(onDeckChunk -> oddsCalculatorTask.calculateOdds(cardsInHand, cardsOnDeck, onDeckChunk, availableCards)).collect(Collectors.toList());
    }

    private List<Set<Card>> getCardsOnDeckCombinations(final Set<Card> cardsOnDeck, final List<Card> availableCards) {
        int requiredLength = FULL_DECK_SIZE - cardsOnDeck.size();
        return requiredLength > 0 ? unOrderedPermutationFactory.getUnOrderedPermutationWithoutRepetition(availableCards, requiredLength) : new ArrayList<>(Collections.singletonList(cardsOnDeck));
    }

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList())
        );
    }

    private Odds combineOdds(final CompletableFuture<List<Odds>> oddsTasks) {
        long winCount = 0;
        long splitCount = 0;
        long totalDealCount = 0;
        for (Odds odds : oddsTasks.join()) {
            winCount += odds.getWinCount();
            splitCount += odds.getSplitCount();
            totalDealCount += odds.getTotalDealCount();
        }
        return new Odds(winCount, splitCount, totalDealCount);
    }


}
