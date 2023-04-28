package com.aussieogame.backend.controller;

import com.aussieogame.backend.model.dto.QueuedBuildingDTO;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.service.QueuedBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("${application.api-prefix}/towns")
@RequiredArgsConstructor
public class QueuedBuildingController {
    private final QueuedBuildingService queueService;

    @PostMapping("/{townId}/queue")
    public ResponseEntity<QueuedBuildingDTO> postNewBuilding(JwtAuthenticationToken principal,
                                                   @PathVariable long townId,
                                                   @RequestBody StartNewBuildingDTO building) {
        QueuedBuildingDTO queuedBuildingDTO = queueService.enqueueNewBuilding(principal.getName(), townId, building);
        URI newQueueItemURI = buildURI(queuedBuildingDTO.getId());

        return createResponse(queuedBuildingDTO, newQueueItemURI);
    }

    private URI buildURI(long queuedItemId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{queuedItemId}")
                .buildAndExpand(queuedItemId)
                .toUri();
    }

    private ResponseEntity<QueuedBuildingDTO> createResponse(QueuedBuildingDTO dto, URI entityLocation) {
        return ResponseEntity
                .created(entityLocation)
                .body(dto);
    }
}
