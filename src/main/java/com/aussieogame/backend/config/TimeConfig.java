package com.aussieogame.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class TimeConfig {
    @Bean
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
