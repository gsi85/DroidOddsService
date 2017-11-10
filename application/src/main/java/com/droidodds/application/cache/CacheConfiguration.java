package com.droidodds.application.cache;

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
        return new CaffeineCacheManager();
    }
}
