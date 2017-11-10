package com.droidodds.application.cache;

import java.util.Arrays;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Laszlo_Sisa
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(Cache fiveCardCache, Cache sevenCardCache) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(fiveCardCache, sevenCardCache));
        return simpleCacheManager;
    }

    @Bean
    protected Cache fiveCardCache() {
        ConcurrentLimitedSizedHashMap<Object, Object> store = new ConcurrentLimitedSizedHashMap<>(500000);
        return new ConcurrentMapCache("fiveCardCache", store, false);
    }

    @Bean
    protected Cache sevenCardCache() {
        ConcurrentLimitedSizedHashMap<Object, Object> store = new ConcurrentLimitedSizedHashMap<>(500000);
        return new ConcurrentMapCache("sevenCardCache", store, false);
    }

}
