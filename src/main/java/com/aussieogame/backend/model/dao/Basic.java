package com.aussieogame.backend.model.dao;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@SuperBuilder
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Basic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
