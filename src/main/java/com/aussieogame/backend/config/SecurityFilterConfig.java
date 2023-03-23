package com.aussieogame.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final AuthenticationErrorHandler authenticationErrorHandler;
    private final OAuth2ResourceServerProperties oauth2Properties; //oauth2 config (extracted from application.yml)
    private final AppProperties appProperties; //custom config properties

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
                        .decoder(createJwtDecoder())
                        .jwtAuthenticationConverter(createAuthenticationConverter())
                );
    }

    private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> configureRequestMatchers() {
        return auth -> auth
                .requestMatchers(getProtectedEndpoints())
                .authenticated()
                .anyRequest()
                .permitAll();
    }

    private JwtDecoder createJwtDecoder() {
        String issuerUri = oauth2Properties.getJwt().getIssuerUri();

        DelegatingOAuth2TokenValidator<Jwt> tokenValidator = createValidatorWithIssuerUri(issuerUri);

        return createDecoderWithIssuerUriValidator(issuerUri, tokenValidator);
    }

    private DelegatingOAuth2TokenValidator<Jwt> createValidatorWithIssuerUri(String issuerUri) {
        OAuth2TokenValidator<Jwt> validatorWithIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        return new DelegatingOAuth2TokenValidator<>(validatorWithIssuer, this::validateAudience);
    }

    private NimbusJwtDecoder createDecoderWithIssuerUriValidator(
            String issuerUri,
            DelegatingOAuth2TokenValidator<Jwt> tokenValidator
    ) {
        NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuerUri);
        decoder.setJwtValidator(tokenValidator);
        return decoder;
    }

    /**
     * Verifies the 'aud' claim, which proves that the token was issued for this API.
     * @param   jwt
     *          The JWT credential.
     * @return
     *          success() if the 'aud' claim is valid, failure() otherwise.
     */
    private OAuth2TokenValidatorResult validateAudience(Jwt jwt) {
        if (containsIntendedAudience(jwt)) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error invalidTokenError = prepareError();
        return OAuth2TokenValidatorResult.failure(invalidTokenError);
    }

    private boolean containsIntendedAudience(Jwt jwt) {
        return jwt.getAudience().contains(appProperties.getAudience());
    }

    private OAuth2Error prepareError() {
        return new OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "JWT validation failed: The token was not issued for this audience",
                "https://datatracker.ietf.org/doc/html/rfc6750#section-3.1"
        );
    }

    private JwtAuthenticationConverter createAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = createAuthoritiesConverter();

        return createAuthenticationConverter(authoritiesConverter);
    }

    private JwtAuthenticationConverter createAuthenticationConverter(JwtGrantedAuthoritiesConverter authoritiesConverter) {
        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authConverter;
    }

    private JwtGrantedAuthoritiesConverter createAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("permissions");
        return authoritiesConverter;
    }

    private String[] getProtectedEndpoints() {
        return addApiPrefix(appProperties.getProtectedEndpoints());
    }

    private String[] addApiPrefix(String[] endpointUrls) {
        return Arrays.stream(endpointUrls)
                .map(url -> appProperties.getApiPrefix() + url)
                .toArray(String[]::new);
    }
}
