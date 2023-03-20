package com.aussieogame.backend.service;

import com.aussieogame.backend.dto.ApiOkResponse;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameApiService {
    private final TownRepository townRepo;

    public ApiOkResponse getProtectedResource() {
        String somethingProtected = "this is available to authenticated users";

        return ApiOkResponse.from(somethingProtected);
    }

    public ApiOkResponse getPublicResource() {
        String somethingPublic = "this is available to everyone";

        return ApiOkResponse.from(somethingPublic);
    }

    public ApiOkResponse getTowns(JwtAuthenticationToken principal) {
        List<Town> towns = townRepo.findAllByUser_Username(principal.getName());

        String response = towns.stream()
                .map(Town::toString)
                .collect(Collectors.joining("  ---  "));
        return ApiOkResponse.from(response);
    }
}
