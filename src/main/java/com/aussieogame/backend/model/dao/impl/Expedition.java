package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expedition extends Basic {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @ManyToOne
    private Resources cargo;
    @ManyToOne
    private Army army;
    @ManyToOne
    private Town targetTown;
    @ManyToOne
    private User user;
}
