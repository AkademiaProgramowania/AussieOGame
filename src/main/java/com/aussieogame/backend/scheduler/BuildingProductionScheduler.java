package com.aussieogame.backend.scheduler;

import com.aussieogame.backend.repo.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BuildingProductionScheduler {

    private final BuildingRepository buildingRepository;

    @Scheduled(cron = "0 * * * * *") // Runs every minute
    public void updateBuildingIsFinished() {
        buildingRepository.findByIsFinishedIsFalseAndEndIsGreaterThanEqual(LocalDateTime.now())
                .stream()
                .peek(building -> building.setIsFinished(true))
                .forEach(buildingRepository::save);
    }
}
