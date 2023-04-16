package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    List<Town> findAllByUsername(String username);

    List<Town> findByUserIdAndBuildingsIsFinishedIsFalse(Long userId);
}
