package com.aussieogame.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class QueuedBuildingDTO {
    private Long id;
    private BuildingDTO building;
    private String townName;
    private ResourcesDTO constructionCost;
    private LocalDateTime constructionStart;
    private LocalDateTime constructionEnd;
}
