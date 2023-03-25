package com.aussieogame.backend.config.security;

import com.aussieogame.backend.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithAudienceJwtDecoderFactory {
    //oauth2 config (extracted from application.yml)
    private final OAuth2ResourceServerProperties oauth2Properties;
    //custom config properties
    private final AppProperties appProperties;


    public JwtDecoder create() {
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
}
