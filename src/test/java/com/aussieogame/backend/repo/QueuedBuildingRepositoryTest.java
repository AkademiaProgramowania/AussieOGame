package com.aussieogame.backend.repo;

import com.aussieogame.backend.model.dao.BaseEntity;
import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QueuedBuildingRepositoryTest {
    @Autowired
    private QueuedBuildingRepository underTest;
    @Test
    @DisplayName("findByConstructionEndBefore() should only return items finishing before given datetime")
    @Sql(value = "classpath:sql/queued-buildings-init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    void whenFindByConstructionEndBefore_thenFindsCorrect() {
        //given
        LocalDateTime givenDatetime = LocalDateTime.of(2000, 1, 1, 11, 11, 11);
        int expectedSize = 3;

        //when
        Set<QueuedBuilding> actual = underTest.findByConstructionEndBefore(givenDatetime);

        //then
        assertThat(actual)
                .hasSize(expectedSize);
        assertThat(actual)
                .extracting(BaseEntity::getId)
                .containsExactlyInAnyOrder(1L, 3L, 5L);
    }

    @Test
    @DisplayName("findByConstructionEndBefore() should return a non-null empty set if no items are finishing")
    void whenFindByConstructionEndBefore_thenFindsNothing() {
        //given
        LocalDateTime givenDatetime = LocalDateTime.of(2000, 1, 1, 11, 11, 11);
        //and empty DB

        //when
        Set<QueuedBuilding> actual = underTest.findByConstructionEndBefore(givenDatetime);

        //then
        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }
}
