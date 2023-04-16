package com.aussieogame.backend.controller;

import com.aussieogame.backend.mapper.ResourcesDetailsMapper;
import com.aussieogame.backend.model.dto.ResourcesDetailsDTO;
import com.aussieogame.backend.service.ResourceDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resource-details")
@RequiredArgsConstructor
public class ResourceDetailsController {

    private final ResourcesDetailsMapper resourcesDetailsMapper;
    private final ResourceDetailsService resourceDetailsService;

    @GetMapping("/{userId}")
    public List<ResourcesDetailsDTO> getStatus(@PathVariable Long userId, @RequestParam LocalDateTime dateTime) {
        return resourcesDetailsMapper.toDtos(resourceDetailsService.getStatus(userId, dateTime));
    }
}
