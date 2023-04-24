package com.aussieogame.backend.model.dao.impl;

import com.aussieogame.backend.model.dao.Basic;
import jakarta.persistence.Entity;
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
// maybe change for settlement
public class Resources extends Basic {
    private Long dollars = 0L;
    private Long eucalyptus = 0L;
}
