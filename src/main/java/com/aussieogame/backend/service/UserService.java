package com.aussieogame.backend.service;

import com.aussieogame.backend.model.dao.enumeration.Race;
import com.aussieogame.backend.model.dao.impl.User;
import com.aussieogame.backend.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // zeby zadzialalo dropnij tabelke user w bazie albo recznie dodaj index na username w sqlu
    public User register(String displayName, JwtAuthenticationToken principal) {
        return userRepository.save(User.builder()
                .displayName(displayName)
                .username(principal.getName())
                .points(0L)
                .race(Race.PLATYPUS)
                .build());
    }

    public String getDisplayName(JwtAuthenticationToken principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(EntityNotFoundException::new)
                .getDisplayName();
    }
}
