package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Leaderboard extends Basic {
    private Long id;
    private Long userId;
    private Long raceId;
    private Long allianceId;
    private Long points;
}
