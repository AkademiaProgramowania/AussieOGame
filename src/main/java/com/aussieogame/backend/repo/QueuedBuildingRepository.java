package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;

public interface QueuedBuildingRepository extends JpaRepository<QueuedBuilding, Long> {
    Set<QueuedBuilding> findByConstructionEndBefore(LocalDateTime threshold);
}
