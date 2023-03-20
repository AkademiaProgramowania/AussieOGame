package com.aussieogame.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final AuthenticationErrorHandler authenticationErrorHandler;
    private final OAuth2ResourceServerProperties oauth2Properties;
    private final AppProperties appProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/protected", "/api/v1/user", "/api/v1/towns")
                            .authenticated()
                        .anyRequest()
                            .permitAll())
                .oauth2ResourceServer(server -> server.authenticationEntryPoint(authenticationErrorHandler)
                        .jwt(jwt -> jwt.decoder(createJwtDecoder())
                                .jwtAuthenticationConverter(createAuthenticationConverter()))
                )
                .cors();

        return http.build();
    }

    private JwtDecoder createJwtDecoder() {
        String issuerUri = oauth2Properties.getJwt().getIssuerUri();

        OAuth2TokenValidator<Jwt> validatorWithIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        DelegatingOAuth2TokenValidator<Jwt> tokenValidator =
                new DelegatingOAuth2TokenValidator<>(validatorWithIssuer, this::validateAudience);

        NimbusJwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuerUri);
        decoder.setJwtValidator(tokenValidator);

        return decoder;
    }

    private OAuth2TokenValidatorResult validateAudience(Jwt jwt) {
        if (jwt.getAudience().contains(appProperties.getAudience())) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error invalidTokenError = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_TOKEN,
                "JWT validation failed: The token was not issued for this audience",
                "https://datatracker.ietf.org/doc/html/rfc6750#section-3.1"
        );
        return OAuth2TokenValidatorResult.failure(invalidTokenError);
    }

    private JwtAuthenticationConverter createAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return authConverter;
    }
}
