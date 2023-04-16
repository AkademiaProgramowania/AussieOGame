package com.aussieogame.backend.model.dto;

import com.aussieogame.backend.model.dao.enumeration.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TownDTO {
    private Long id;
    private String name;
    private Region region;
    private int size;
    //This list is like Active production: Name (building), Start timestamp, End timestamp
    private List<BuildingDTO> buildings;
}
