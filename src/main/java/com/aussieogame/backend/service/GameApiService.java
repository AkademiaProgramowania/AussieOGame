package com.aussieogame.backend.service;

import com.aussieogame.backend.dto.api.ApiOkResponse;
import com.aussieogame.backend.model.dao.enumeration.Race;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dao.impl.User;
import com.aussieogame.backend.repo.TownRepository;
import com.aussieogame.backend.repo.UserRepository;
import com.aussieogame.backend.service.exception.RegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameApiService {
    private final TownRepository townRepo;
    private final UserRepository userRepo;

    public ApiOkResponse getTowns(JwtAuthenticationToken principal) {
        List<Town> towns = townRepo.findAllByUserUsername(principal.getName());

        String responseText = prepareResponseTextFrom(towns);

        // TODO fix this once we have DTOs (use Optionals and the ResponseEntity<ApiResponse> up the chain)
        return ApiOkResponse.from(responseText);
    }

    public Optional<String> getDisplayName(JwtAuthenticationToken principal) {
        Optional<User> registration = userRepo.findByUsername(principal.getName());
        return registration.map(User::getDisplayName);
    }

    public User assignRegistrationToUser(String newDisplayName,
                                         JwtAuthenticationToken principal
    ) throws RegistrationException {
        String principalName = principal.getName();

        failIfAlreadyRegistered(principalName);

        User newRegistration = createNewUser(newDisplayName, principalName);

        // TODO handle actual DB exceptions
        return userRepo.save(newRegistration);
    }

    private String prepareResponseTextFrom(Collection<Town> towns) {
        return towns.stream()
                .map(Town::toString)
                .collect(Collectors.joining(" --- "));
    }

    private void failIfAlreadyRegistered(String principalName) throws RegistrationException {
        if (userRepo.existsByUsername(principalName)) {
            throw new RegistrationException("Registration already exists.");
        }
    }

    private User createNewUser(String displayName, String username) {
        return new User(displayName, username, 0L, new HashSet<>(), Race.PLATYPUS);
    }
}
