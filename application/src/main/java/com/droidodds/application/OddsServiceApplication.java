package com.droidodds.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.droidodds")
public class OddsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OddsServiceApplication.class, args);
    }
}
