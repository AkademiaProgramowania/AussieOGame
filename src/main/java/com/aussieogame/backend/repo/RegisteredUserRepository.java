package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    Optional<RegisteredUser> findByUsername(String jwtSubjectClaim);
}
