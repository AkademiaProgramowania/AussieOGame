package com.aussieogame.backend.service;

import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.repo.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {

    private final TownRepository townRepository;

    public List<Town> getOverview(Long id) {
        return townRepository.findByUserIdAndBuildingsIsFinishedIsFalse(id);
    }

    public List<Town> getTowns(String username) {
        return townRepository.findAllByUsername(username);
    }
}
