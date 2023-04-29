package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.BaseEntity;
import com.aussieogame.backend.model.dao.impl.Resources;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionCostConfig extends BaseEntity {
    private String itemType;
    private Map<Integer, Resources> costByLevel;
}
