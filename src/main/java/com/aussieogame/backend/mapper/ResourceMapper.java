package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dto.ResourcesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    ResourcesDTO toDto(Resources source);
}
