package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import com.aussieogame.backend.model.dao.enumeration.Region;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Town extends Basic {
    private String name;
    private int size;
    @Enumerated(EnumType.STRING)
    private Region region;
    @ManyToOne
    private User user;
    @OneToOne
    private Resources resources = new Resources();
    @OneToMany(mappedBy = "town", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Building> buildings = new HashSet<>();

    @OneToMany(mappedBy = "town", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Building> constructionQueue = new HashSet<>();

    public void addToConstructionQueue(Building building) {
        building.setTown(this);
        constructionQueue.add(building);
    }
}
