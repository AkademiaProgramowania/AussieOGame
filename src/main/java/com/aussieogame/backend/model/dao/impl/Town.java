package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.BaseEntity;
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
public class Town extends BaseEntity {
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
    private Set<QueuedBuilding> constructionQueue = new HashSet<>();

    public void addToBuildingQueue(QueuedBuilding queued) {
        queued.setTown(this);
        constructionQueue.add(queued);
    }

    public void moveBuildingFromQueueToFinished(QueuedBuilding queued) {
        addFinishedBuilding(queued.getBuilding());
        removeFromBuildingQueue(queued);
    }

    private void removeFromBuildingQueue(QueuedBuilding queued) {
        queued.setTown(null);
        constructionQueue.remove(queued);
    }

    private void addFinishedBuilding(Building building) {
        building.setTown(this);
        buildings.add(building);
    }
}
