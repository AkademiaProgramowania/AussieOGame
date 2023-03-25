package com.aussieogame.backend.config.security;

import com.aussieogame.backend.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final AuthenticationErrorHandler authenticationErrorHandler;

    private final WithAudienceJwtDecoderFactory jwtDecoderFactory;
    private final AuthenticationConverterFactory authenticationConverterFactory;

    //custom config properties
    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configureRequestMatchers())
                .oauth2ResourceServer(configureOAuth2())
                .cors();

        return http.build();
    }

    private Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> configureOAuth2() {
        return server -> server
                .authenticationEntryPoint(authenticationErrorHandler)
                .jwt(jwt -> jwt
                        .decoder(jwtDecoderFactory.create())
                        .jwtAuthenticationConverter(authenticationConverterFactory.create())
                );
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
    configureRequestMatchers() {
        return auth -> auth
                // TODO we should actually allow certain endpoints and restrict everything else
                .requestMatchers(appProperties.getProtectedEndpointUrls())
                    .authenticated()
                .anyRequest()
                    .permitAll();
    }

}
