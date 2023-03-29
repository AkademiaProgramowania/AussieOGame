package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Building extends Basic {
    private Long id;
    private String name;
    private int level;
    @ManyToOne
    private Resources constructionCost;
    private String description;
    @ManyToOne
    private Town town;
    @OneToOne
    private Resources resources;
}
