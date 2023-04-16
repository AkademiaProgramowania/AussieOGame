package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dto.BuildingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    BuildingDTO toDTO(Building building);
}
