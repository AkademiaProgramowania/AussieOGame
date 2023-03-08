package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleUnitTypeArmy extends Basic {
    private String unitType;
    private int count;
}
