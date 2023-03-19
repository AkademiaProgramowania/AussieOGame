package com.aussieogame.backend.controller;

import com.aussieogame.backend.dto.ApiOkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    // TODO https://auth0.com/docs/api/authentication#user-profile

    @GetMapping
    public ApiOkResponse userTest(JwtAuthenticationToken principal) {
        String subject = principal.getName();
        StringBuilder claims = new StringBuilder(subject);

        principal.getTokenAttributes().forEach((key, value) -> claims
                .append("  ##  ")
                .append(key)
                .append(":")
                .append(value)
        );
        return ApiOkResponse.from(claims.toString());
    }
}

