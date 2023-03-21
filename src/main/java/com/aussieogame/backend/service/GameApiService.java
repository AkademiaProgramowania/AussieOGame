package com.aussieogame.backend.service;

import com.aussieogame.backend.dto.ApiErrorResponse;
import com.aussieogame.backend.dto.ApiOkResponse;
import com.aussieogame.backend.dto.ApiResponse;
import com.aussieogame.backend.model.dao.enumeration.Race;
import com.aussieogame.backend.model.dao.impl.Registration;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dao.impl.User;
import com.aussieogame.backend.repo.RegistrationRepository;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameApiService {
    private final TownRepository townRepo;
    private final RegistrationRepository registrationRepo;

    public ApiOkResponse getProtectedResource() {
        String somethingProtected = "this is available to authenticated users";

        return ApiOkResponse.from(somethingProtected);
    }

    public ApiOkResponse getPublicResource() {
        String somethingPublic = "this is available to everyone";

        return ApiOkResponse.from(somethingPublic);
    }

    public ApiOkResponse getTowns(JwtAuthenticationToken principal) {
        List<Town> towns = townRepo.findAllByUserRegistrationUsername(principal.getName());

        String response = towns.stream()
                .map(Town::toString)
                .collect(Collectors.joining("  ---  "));
        return ApiOkResponse.from(response);
    }

    public ApiResponse getDisplayName(JwtAuthenticationToken principal) {
        Optional<Registration> registration = registrationRepo.findByUsername(principal.getName());
        if (registration.isPresent()) {
            String displayName = registration.get().getDisplayName();
            return ApiOkResponse.from(displayName);
        }
        return new ApiErrorResponse("Registration not found");
    }

    public ApiResponse assignNameToUser(String newName, JwtAuthenticationToken principal) {
        String subject = principal.getName();
        if (registrationRepo.existsByUsername(subject)) {
            return new ApiErrorResponse("Registration already exists");
        }

        User newUser = new User(0L, new HashSet<>(), Race.PLATYPUS, null);
        Registration newRegistration = new Registration(subject, newName, newUser);
        newUser.setRegistration(newRegistration);

        registrationRepo.save(newRegistration);
        return new ApiOkResponse("Created.");
    }
}
