package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.BaseEntity;
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
public class Expedition extends BaseEntity {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @OneToOne
    private Resources cargo;
    @ManyToOne
    private Army army;
    @ManyToOne
    private Town targetTown;
    @ManyToOne
    private User user;
}
