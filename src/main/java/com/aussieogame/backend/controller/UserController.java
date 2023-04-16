package com.aussieogame.backend.controller;

import com.aussieogame.backend.mapper.DisplayNameMapper;
import com.aussieogame.backend.mapper.UserMapper;
import com.aussieogame.backend.model.dto.DisplayNameDTO;
import com.aussieogame.backend.model.dto.UserDTO;
import com.aussieogame.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DisplayNameMapper displayNameMapper;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    // teraz jest JwtAuthenticationToken defaultowo w @RequestParam a w postcie w sumie powinno byc to w body czyli jedno dto zawierajace te wartosci.
    public UserDTO register(@RequestParam("name") String displayName, JwtAuthenticationToken principal) {
        return userMapper.toDto((userService.register(displayName, principal)));
    }

    @GetMapping
    public DisplayNameDTO getDisplayName(JwtAuthenticationToken principal) {
        return displayNameMapper.toDto(userService.getDisplayName(principal));
    }
}