package com.happytown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class Application {

    @Bean
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
