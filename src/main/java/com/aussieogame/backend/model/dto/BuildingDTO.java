package com.aussieogame.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BuildingDTO {
    // kolejnosc dla mapperow mapstructa nie ma znaczenia
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
}
