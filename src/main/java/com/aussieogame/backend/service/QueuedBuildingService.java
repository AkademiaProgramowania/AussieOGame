package com.aussieogame.backend.service;

import com.aussieogame.backend.exception.ResourceNotFoundException;
import com.aussieogame.backend.mapper.QueuedBuildingMapper;
import com.aussieogame.backend.mapper.TownMapper;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.QueuedBuildingDTO;
import com.aussieogame.backend.model.dto.ResourcesDTO;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.repo.TownRepository;
import com.aussieogame.backend.service.utils.ConstructionAvailabilityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueuedBuildingService {
    private final TownRepository townRepo;
    private final QueuedBuildingMapper mapper;
    private final ConstructionAvailabilityValidator constructionValidator;
    private final Clock clock;

    @Transactional
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

    private QueuedBuilding addToQueueIfAllowedOrElseThrow(Town town, StartNewBuildingDTO requestData) {
        constructionValidator.checkIfAllowedOrElseThrow(town, requestData);
        ResourcesDTO constructionCost = findConstructionCost(town, requestData);
        subtractFundsIfSufficientOrElseThrow(town, constructionCost);
        Building newBuildingEntity = createBuilding(requestData);
        QueuedBuilding newQueuedEntity = createQueuedBuilding(town, newBuildingEntity);
        saveTownWithUpdatedQueue(town, newQueuedEntity);

        return newQueuedEntity;
    }

    private void saveTownWithUpdatedQueue(Town town, QueuedBuilding newQueuedEntity) {
    }

    private QueuedBuilding createQueuedBuilding(Town town, Building newBuildingEntity) {
        throw new UnsupportedOperationException();

    }

    private Building createBuilding(StartNewBuildingDTO requestData) {
        throw new UnsupportedOperationException();

    }

    private void subtractFundsIfSufficientOrElseThrow(Town town, ResourcesDTO constructionCost) {
    }

    private ResourcesDTO findConstructionCost(Town town, StartNewBuildingDTO requestData) {
        throw new UnsupportedOperationException();
    }

}
