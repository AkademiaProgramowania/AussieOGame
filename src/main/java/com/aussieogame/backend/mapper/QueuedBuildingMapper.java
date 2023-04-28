package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dto.QueuedBuildingDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {
                BuildingMapper.class,
                ResourceMapper.class
        })
public interface QueuedBuildingMapper {
    @Mapping(target = "townName", source = "town.name")
    QueuedBuildingDTO toDto(QueuedBuilding queuedBuilding);
}
