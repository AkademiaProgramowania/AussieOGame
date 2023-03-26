package com.aussieogame.backend.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationConverterFactory {

    public JwtAuthenticationConverter create() {
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

}
