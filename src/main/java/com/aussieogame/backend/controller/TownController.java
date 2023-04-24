package com.aussieogame.backend.controller;

import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.model.dto.TownDTO;
import com.aussieogame.backend.service.TownService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${application.api-prefix}/towns")
@RequiredArgsConstructor
public class TownController {
    private final TownService townService;

    /**
     * Retrieves all towns owned by the currently authenticated user, with their full building list (including active
     * production).
     * @param principal Authentication principal (JWT).
     */
    @GetMapping
    public ResponseEntity<List<TownDTO>> getTowns(JwtAuthenticationToken principal) {
        List<TownDTO> townDTOs = townService.getTowns(principal.getName());

        return ResponseEntity.ok(townDTOs);
    }

    @GetMapping("/{townId}")
    public ResponseEntity<TownDTO> getTownById(JwtAuthenticationToken principal,
                                               @PathVariable long townId) {
        TownDTO townDTO = townService.getById(principal.getName(), townId);

        return ResponseEntity.ok(townDTO);
    }

    @PostMapping("/{townId}/queue")
    public ResponseEntity<TownDTO> postNewBuilding(JwtAuthenticationToken principal,
                                                   @PathVariable long townId,
                                                   @RequestBody StartNewBuildingDTO building) {
        TownDTO townDTO = townService.enqueueNewBuilding(principal.getName(), townId, building);
        URI newTownURI = getNewTownURI(townDTO.getId());

        return createResponse(townDTO, newTownURI);
    }

    private URI getNewTownURI(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private ResponseEntity<TownDTO> createResponse(TownDTO createdTown, URI entityLocation) {
        return ResponseEntity
                .created(entityLocation)
                .body(createdTown);
    }
}
