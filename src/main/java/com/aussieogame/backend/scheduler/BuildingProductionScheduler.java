package com.aussieogame.backend.scheduler;

import com.aussieogame.backend.repo.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BuildingProductionScheduler {

    private final BuildingRepository buildingRepository;

    @Scheduled(cron = "0 * * * * *") // Runs every minute
    public void updateBuildingIsFinished() {
        buildingRepository.findByIsFinishedIsFalseAndConstructionEndIsGreaterThanEqual(LocalDateTime.now())
                .stream()
                .peek(building -> building.setIsFinished(true))
                .forEach(buildingRepository::save);
    }
}
