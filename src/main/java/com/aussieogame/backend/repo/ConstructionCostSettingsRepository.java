package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.ConstructionCostSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConstructionCostSettingsRepository extends JpaRepository<ConstructionCostSettings, Long> {
    Optional<ConstructionCostSettings> findByItemNameIgnoreCase(String name);
}
