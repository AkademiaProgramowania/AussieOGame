package com.aussieogame.backend.controller;

import com.aussieogame.backend.dto.api.ApiResponse;
import com.aussieogame.backend.dto.api.mapper.SimpleResponseFactory;
import com.aussieogame.backend.model.dao.impl.User;
import com.aussieogame.backend.service.GameApiService;
import com.aussieogame.backend.service.exception.RegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final GameApiService api;

    private final SimpleResponseFactory responseFactory;


    // TODO https://auth0.com/docs/api/authentication#user-profile

    @GetMapping
    public ResponseEntity<ApiResponse> getPlayerDisplayName(JwtAuthenticationToken principal) {
        return api.getDisplayName(principal)
                .map(responseFactory::ok)
                .orElseGet(() -> responseFactory.notFound("Registration not found."));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postPlayerDisplayName(@RequestParam("name") String name, JwtAuthenticationToken principal) {
        try {
            User newUser = api.assignRegistrationToUser(name, principal);
            return responseFactory.created(newUser.getDisplayName());
        } catch (RegistrationException e) {
            return responseFactory.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}