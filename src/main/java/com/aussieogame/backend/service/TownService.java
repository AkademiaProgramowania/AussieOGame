package com.aussieogame.backend.service;

import com.aussieogame.backend.exception.ResourceNotFoundException;
import com.aussieogame.backend.mapper.TownMapper;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.repo.BuildingRepository;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepo;
    private final BuildingRepository buildingRepo;
    private final TownMapper mapper;

    public List<TownDTO> getTowns(String username) {
        return townRepo.findAllByUserUsername(username).stream()
                .map(mapper::toDto)
                .toList();
    }

    public TownDTO getById(String username, long id) {
        return townRepo.findByIdAndUserUsername(id, username)
                .map(mapper::toDto)
                .orElseThrow(() -> buildException(id));
    }

    public TownDTO enqueueNewBuilding(
            String username,
            long townId,
            StartNewBuildingDTO buildingToStart
    ) {
        return townRepo.findByIdAndUserUsername(townId, username)
                .map(town -> saveWithNewBuilding(town, buildingToStart))
                .map(mapper::toDto)
                .orElseThrow(() -> buildException(townId));
    }

    private ResourceNotFoundException buildException(long id) {
        String msg = """
                No town belonging to the current user
                found with ID %d.
                """.formatted(id);
        return new ResourceNotFoundException(msg);
    }

    private Town saveWithNewBuilding(Town town, StartNewBuildingDTO buildingDTO) {

        Building newBuildingEntity = Building.builder()
                .name(buildingDTO.getName())
                .level(buildingDTO.getLevel())
                .description("Some New Building")
                .isFinished(false)
                .constructionCost(new Resources())
                .constructionStart(LocalDateTime.now())
                .constructionEnd(LocalDateTime.now().plusSeconds(10))
                .build();

        town.addToConstructionQueue(newBuildingEntity);
        return townRepo.saveAndFlush(town);
    }
}
