package com.aussieogame.backend.scheduler;

import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.repo.QueuedBuildingRepository;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@ConditionalOnProperty(value = "application.scheduling.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BuildingQueueScheduler {
    private final QueuedBuildingRepository queueRepo;
    private final TownRepository townRepo;

    @Scheduled(fixedRateString = "${application.scheduling.poll-building-queue}")
    @Transactional
    public void completeQueuedBuildings() {
        LocalDateTime now = LocalDateTime.now();
        queueRepo.findByConstructionEndBefore(now)
                .stream()
                .peek(this::saveAsFinishedInTown)
                .forEach(queueRepo::delete);
    }

    protected void saveAsFinishedInTown(QueuedBuilding queued) {
        Town town = queued.getTown();

        town.moveBuildingFromQueueToFinished(queued);

        townRepo.save(town);
    }
}
