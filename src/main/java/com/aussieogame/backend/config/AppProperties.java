package com.aussieogame.backend.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Getter
@AllArgsConstructor
public class AppProperties {
    private final String clientOriginUrl;
    private final String audience;
}
