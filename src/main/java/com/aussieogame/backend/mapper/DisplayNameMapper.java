package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dto.DisplayNameDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DisplayNameMapper {
    DisplayNameDTO toDto(String displayName);
}
