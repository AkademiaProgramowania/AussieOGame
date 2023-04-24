package com.aussieogame.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StartNewBuildingDTO {
    private String name;
    private int level;
}
