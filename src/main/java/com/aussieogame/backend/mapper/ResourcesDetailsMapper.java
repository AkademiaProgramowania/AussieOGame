package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.ResourcesDetails;
import com.aussieogame.backend.model.dto.ResourcesDetailsDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // komponent springowy sie tworzy
public interface ResourcesDetailsMapper {
    List<ResourcesDetailsDTO> toDtos(List<ResourcesDetails> resourcesDetails);
}
