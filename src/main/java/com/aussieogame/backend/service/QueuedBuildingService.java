package com.aussieogame.backend.service;

import com.aussieogame.backend.exception.ResourceNotFoundException;
import com.aussieogame.backend.mapper.QueuedBuildingMapper;
import com.aussieogame.backend.mapper.TownMapper;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.QueuedBuildingDTO;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueuedBuildingService {
    private final TownRepository townRepo;
    private final QueuedBuildingMapper mapper;
    private final Clock clock;
    public QueuedBuildingDTO enqueueNewBuilding(
            String username,
            long townId,
            StartNewBuildingDTO buildingToStart
    ) {
        return townRepo.findByIdAndUserUsername(townId, username)
                .flatMap(town -> startNewBuildingIfAllowed(town, buildingToStart))
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(townId));
    }

    private Optional<QueuedBuilding> startNewBuildingIfAllowed(Town town, StartNewBuildingDTO buildingDTO) {
        Building newBuildingEntity = Building.builder()
                .name(buildingDTO.getName())
                .level(buildingDTO.getLevel())
                .description("Some New Building")
                .town(null) //Unfinished buildings are not directly attached to Towns
                .build();
        QueuedBuilding newQueuedEntity = QueuedBuilding.builder()
                .building(newBuildingEntity)
                .town(town)
                .constructionCost(new Resources())
                .constructionStart(LocalDateTime.now(clock))
                .constructionEnd(LocalDateTime.now(clock).plusSeconds(10))
                .build();

        town.addToBuildingQueue(newQueuedEntity);
        townRepo.saveAndFlush(town);

        return Optional.of(newQueuedEntity);
    }
}
