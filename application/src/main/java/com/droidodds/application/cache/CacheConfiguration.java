package com.droidodds.application.cache;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Laszlo_Sisa
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("fiveCardCache", "sevenCardCache");
        caffeineCacheManager.setCaffeineSpec(CaffeineSpec.parse("initialCapacity=2500000,maximumSize=5000000"));
        return caffeineCacheManager;
    }

}
