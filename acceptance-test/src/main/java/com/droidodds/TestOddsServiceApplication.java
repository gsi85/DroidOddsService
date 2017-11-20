package com.droidodds;

import com.droidodds.application.ExcludeFromTests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Laszlo_Sisa
 */
@SpringBootApplication(scanBasePackages = "com.droidodds")
@EnableCaching
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromTests.class))
public class TestOddsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestOddsServiceApplication.class, args);
    }
}
