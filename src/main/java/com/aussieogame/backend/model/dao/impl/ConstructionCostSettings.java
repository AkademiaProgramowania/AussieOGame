package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.BaseEntity;
import jakarta.persistence.*;
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
public class ConstructionCostSettings extends BaseEntity {
    private String itemName;
    @OneToOne
    private Resources baseCost;
    @OneToOne
    private Resources linearIncrement;
    private Double exponentialDollarsFactor;
    private Double exponentialEucalyptusFactor;
}
