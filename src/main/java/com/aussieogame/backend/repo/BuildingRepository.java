package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByIsFinishedIsFalseAndConstructionEndIsGreaterThanEqual(LocalDateTime now);
}
