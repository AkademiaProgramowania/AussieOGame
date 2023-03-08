package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import com.aussieogame.backend.model.dao.enumeration.Region;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @ManyToOne
    private Resources resources;
    @OneToMany(mappedBy = "town")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Building> buildings;
}
