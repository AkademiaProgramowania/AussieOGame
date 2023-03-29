package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import com.aussieogame.backend.model.dao.enumeration.Race;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`user`") //user is a reserved keyword in some SQL dialects
public class User extends Basic {
    //"username" is semi-reserved by Spring Security to mean a unique Principal id
    private String displayName;
    @Column(unique = true)
    private String username; //used in Spring Security
    private Long points;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Town> towns;
    @Enumerated(EnumType.STRING)
    private Race race;
}
