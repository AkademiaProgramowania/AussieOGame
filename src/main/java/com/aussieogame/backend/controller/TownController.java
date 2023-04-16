package com.aussieogame.backend.controller;

import com.aussieogame.backend.mapper.TownMapper;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.service.TownService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/town")
@RequiredArgsConstructor
public class TownController {
    private final TownService townService;
    private final TownMapper townMapper;

    /**
     * @return list of towns owned by the user. In the towns there are only buildings that are currently still being built.
     * */
    @GetMapping("/{userId}")
    public List<TownDTO> getOverview(@PathVariable Long userId) {
        return townMapper.toDtos(townService.getOverview(userId));
    }

    // w sumie to request param nie zadziala jak to jest jakas magiczna zmienna
    @GetMapping
    public List<TownDTO> getTowns( JwtAuthenticationToken principal) {
        return townMapper.toDtos(townService.getTowns(principal.getName()));
    }
}
