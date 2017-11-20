package com.droidodds.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.droidodds")
@EnableCaching
@ExcludeFromTests
public class OddsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OddsServiceApplication.class, args);
    }
}
