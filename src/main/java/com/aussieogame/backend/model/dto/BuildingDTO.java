package com.aussieogame.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BuildingDTO {
    private String name;
    private Integer level;
    private String description;
}


