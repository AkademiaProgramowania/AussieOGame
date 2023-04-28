package com.aussieogame.backend.service;

import com.aussieogame.backend.exception.ResourceNotFoundException;
import com.aussieogame.backend.mapper.TownMapper;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepo;
    private final TownMapper mapper;

    public List<TownDTO> getTowns(String username) {
        return townRepo.findAllByUserUsername(username).stream()
                .map(mapper::toDto)
                .toList();
    }

    public TownDTO getById(String username, long id) {
        return townRepo.findByIdAndUserUsername(id, username)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }


}
