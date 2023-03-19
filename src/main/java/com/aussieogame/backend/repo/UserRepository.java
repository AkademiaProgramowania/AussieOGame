package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String jwtSubjectClaim);
}
