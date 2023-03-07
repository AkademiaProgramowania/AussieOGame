package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Army extends Basic {
    private String name;
    private int totalUnitCount;
    private Long cargoCapacity;
    private int speed;
    private int eucalyptusConsumption;
    @ManyToOne
    private User user;
    @ManyToOne
    private Town returnTown;
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SingleUnitTypeArmy> singleUnitTypeArmies;
}