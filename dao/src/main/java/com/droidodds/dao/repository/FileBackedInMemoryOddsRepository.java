package com.droidodds.dao.repository;

import com.droidodds.domain.card.Card;
import com.droidodds.domain.card.Rank;
import com.droidodds.domain.card.Suit;
import com.droidodds.domain.odds.Odds;
import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Laszlo_Sisa
 */
@Repository
public class FileBackedInMemoryOddsRepository implements OddsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileBackedInMemoryOddsRepository.class);

    private final ConcurrentMap<List<Card>, Odds> oddsMap = new ConcurrentHashMap<>();

    private final File oddsDatabaseFile = new File("C:/innovation/oddsDatabase.json");

    @Override
    public Odds getOdds(final Set<Card> cardsInHand) {
        return oddsMap.get(createKey(cardsInHand));
    }

    @Override
    public void persistOdds(final Set<Card> cardsInHand, final Odds odds) {
        List<Card> key = createKey(cardsInHand);
        oddsMap.put(key, odds);
        flushMapToFile();
    }

    @PostConstruct
    public void loadMap() throws IOException {
        if (!oddsDatabaseFile.exists()) {
            oddsDatabaseFile.createNewFile();
        }
        CharSource source = Files.asCharSource(oddsDatabaseFile, Charsets.UTF_8);
        Type type = new TypeToken<Map<String, Odds>>() {
        }.getType();
        Map<String, Odds> persistedOddsMap = new Gson().fromJson(source.read(), type);
        if (persistedOddsMap != null) {
            for (Map.Entry<String, Odds> entry : persistedOddsMap.entrySet()) {
                String[] cardTextArray = entry.getKey().replace("[", "").replace("]", "").trim().split(",");
                List<Card> inHand = Arrays.stream(cardTextArray).map(String::trim).map(text -> toCard(text)).collect(Collectors.toList());
                oddsMap.put(inHand, entry.getValue());
            }
        }
    }

    private Card toCard(final String text) {
        return new Card(Rank.getByShortName(text.substring(1).toUpperCase()), Suit.getByShortName(text.substring(0, 1).toUpperCase()));
    }

    private List<Card> createKey(final Set<Card> cardsInHand) {
        ArrayList<Card> cards = new ArrayList<>(cardsInHand);
        Collections.sort(cards);
        return cards;
    }

    private void flushMapToFile() {
        synchronized (oddsDatabaseFile) {
            try {
                Files.write(new Gson().toJson(oddsMap), oddsDatabaseFile, Charsets.UTF_8);
            } catch (IOException exception) {
                LOGGER.error("Failed to write odds map to file: {}", exception);
            }
        }
    }
}
