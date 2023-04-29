package com.aussieogame.backend.service.utils;

import com.aussieogame.backend.exception.RequestedActionNotAllowedException;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConstructionAvailabilityValidator {
    public void checkIfAllowedOrElseThrow(Town town, StartNewBuildingDTO buildingData) {
        if (buildingLevelClashesWithExisting(town, buildingData)) {
            throw createException(town, buildingData);
        }

        //more verification logic, for example tech level, some qualities of the Town itself etc.
        //TBD
    }

    private boolean buildingLevelClashesWithExisting(Town town, StartNewBuildingDTO buildingData) {
        Optional<Building> existingBuilding = findExistingBuildingInTown(town, buildingData);

        return !isNewOrUpgradesExisting(buildingData, existingBuilding);
    }

    private Optional<Building> findExistingBuildingInTown(Town town, StartNewBuildingDTO buildingData) {
        Set<Building> buildingsInTown = town.getBuildings();
        return buildingsInTown.stream()
                .filter(building -> building.getName().equals(buildingData.getName()))
                .findFirst();
    }

    private boolean isNewOrUpgradesExisting(StartNewBuildingDTO newDto, Optional<Building> existing) {
        return isNewFirstLevelBuilding(newDto, existing) || upgradesExistingBuildingByOne(newDto, existing);
    }

    private boolean upgradesExistingBuildingByOne(StartNewBuildingDTO buildingData, Optional<Building> existingBuilding) {
        return existingBuilding.isPresent() && existingBuilding.get().getLevel() == buildingData.getLevel() - 1;
    }

    private boolean isNewFirstLevelBuilding(StartNewBuildingDTO buildingData, Optional<Building> existingBuilding) {
        return existingBuilding.isEmpty() && buildingData.getLevel() == 0;
    }

    private RequestedActionNotAllowedException createException(
            Town town,
            StartNewBuildingDTO buildingDTO
    ) {
        String msg = """
                Requested building:
                %s
                clashes with an existing building in Town:
                %s (ID %d)
                """.formatted(buildingDTO, town.getName(), town.getId());
        return new RequestedActionNotAllowedException(msg);
    }
}
