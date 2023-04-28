package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.TownDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BuildingMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TownMapper {
//    List<TownDTO> toDtos(List<Town> towns);
    TownDTO toDto(Town town);
}
