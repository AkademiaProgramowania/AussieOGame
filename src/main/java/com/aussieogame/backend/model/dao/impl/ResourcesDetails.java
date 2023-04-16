package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import com.aussieogame.backend.model.dao.enumeration.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
// maybe change for settlement
public class ResourcesDetails extends Basic {
    // typ operacji np add, minus
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    // wartosc operacji
    private Long dollars;
    private Long eucalyptus;
    // data operacji
    private LocalDateTime dateTime;
    @ManyToOne
    private Resources resources;
}
