package com.aussieogame.backend.controller;

import com.aussieogame.backend.dto.api.ApiOkResponse;
import com.aussieogame.backend.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameApiController {

    private final GameApiService api;

    @GetMapping("/towns")
    public ApiOkResponse getTowns(JwtAuthenticationToken principal) {
        return api.getTowns(principal);
    }
}
