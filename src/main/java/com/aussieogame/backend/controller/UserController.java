package com.aussieogame.backend.controller;

import com.aussieogame.backend.dto.ApiResponse;
import com.aussieogame.backend.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final GameApiService api;


    // TODO https://auth0.com/docs/api/authentication#user-profile

    @GetMapping
    public ApiResponse getPlayerDisplayName(JwtAuthenticationToken principal) {
        return api.getDisplayName(principal);
    }

    @PostMapping
    public ApiResponse postPlayerDisplayName(@RequestParam("name") String name, JwtAuthenticationToken principal) {
        return api.assignNameToUser(name, principal);
    }
}