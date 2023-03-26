package com.aussieogame.backend.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

@ConfigurationProperties(prefix = "application")
@Getter
@AllArgsConstructor
public class AppProperties {
    private final String clientOriginUrl;
    private final String audience;
    private final String apiPrefix;

    @Getter(value = AccessLevel.NONE)
    private final String[] protectedEndpoints;

    public String[] getProtectedEndpointUrls() {
        return getUrlsWithApiPrefix();
    }

    private String[] getUrlsWithApiPrefix() {
        return Arrays.stream(protectedEndpoints)
                .map(endpoint -> apiPrefix + endpoint)
                .toArray(String[]::new);
    }
}