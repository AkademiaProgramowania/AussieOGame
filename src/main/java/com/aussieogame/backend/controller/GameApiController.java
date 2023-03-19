package com.aussieogame.backend.controller;

import com.aussieogame.backend.dto.ApiOkResponse;
import com.aussieogame.backend.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameApiController {

    private final GameApiService api;

    @GetMapping("/protected")
    public ApiOkResponse getProtected() {
        return api.getProtectedResource();
    }

    @GetMapping("/public")
    public ApiOkResponse getPublic() {
        return api.getPublicResource();
    }
}
