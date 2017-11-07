package com.droidodds.application.cache;

import com.droidodds.engine.evaluator.domain.EvaluatedHand;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.cache2k.Cache2kBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Laszlo_Sisa
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(fiveCardCahce(), sevenCardCahce()));
        return simpleCacheManager;
    }

    @Bean
    Cache fiveCardCahce() {
        return new Cache2kAdapter(Cache2kBuilder.of(List.class, EvaluatedHand.class).name("fiveCardCache").eternal(true).entryCapacity(500000).build());
    }

    @Bean
    Cache sevenCardCahce() {
        return new Cache2kAdapter(Cache2kBuilder.of(List.class, EvaluatedHand.class).name("sevenCardCache").eternal(true).entryCapacity(500000).build());
    }
}
