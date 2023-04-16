package com.aussieogame.backend.model.dto;

import com.aussieogame.backend.model.dao.enumeration.Race;
import com.aussieogame.backend.model.dao.impl.Town;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String displayName;
    private String username;
    private Long points;
    private Set<Town> towns;
    private Race race;
}
