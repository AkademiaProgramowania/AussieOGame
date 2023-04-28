package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueuedBuilding extends BaseEntity {
    @OneToOne(cascade = CascadeType.PERSIST)
    private Building building;
    @ManyToOne
    private Town town;
    @OneToOne
    private Resources constructionCost;
    private LocalDateTime constructionStart;
    private LocalDateTime constructionEnd;
}
