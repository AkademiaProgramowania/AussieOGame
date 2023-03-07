package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alliance extends Basic {
    private String name;
    private String description;
    private LocalDate creationDate;
    @ManyToOne
    private User founder;
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> members;
    private Long totalPoints;
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Alliance> allies;
    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Alliance> enemies;
}
