package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUsername(String jwtSubjectClaim);

    boolean existsByUsername(String jwtSubjectClaim);
}
