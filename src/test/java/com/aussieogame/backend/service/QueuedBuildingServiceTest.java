package com.aussieogame.backend.service;

import com.aussieogame.backend.mapper.BuildingMapperImpl;
import com.aussieogame.backend.mapper.QueuedBuildingMapper;
import com.aussieogame.backend.mapper.QueuedBuildingMapperImpl;
import com.aussieogame.backend.mapper.ResourceMapperImpl;
import com.aussieogame.backend.model.dao.enumeration.Region;
import com.aussieogame.backend.model.dao.impl.Building;
import com.aussieogame.backend.model.dao.impl.QueuedBuilding;
import com.aussieogame.backend.model.dao.impl.Resources;
import com.aussieogame.backend.model.dao.impl.Town;
import com.aussieogame.backend.model.dto.QueuedBuildingDTO;
import com.aussieogame.backend.model.dto.StartNewBuildingDTO;
import com.aussieogame.backend.repo.TownRepository;
import com.aussieogame.backend.utils.TestTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueuedBuildingServiceTest {
    @Mock
    private TownRepository mockTownRepo;
    @Mock
    private QueuedBuildingMapper mockQueueMapper;
    @Mock
    private Clock clock;
    @InjectMocks
    private QueuedBuildingService underTest;

    @Test
    @DisplayName("enqueueNewBuilding should return a DTO for the queued building")
    void whenEnqueueNewBuilding_thenQueuesCorrectly() {
        //given
        String principalUsername = "existing-user";
        long townId = 1L;
        StartNewBuildingDTO startBuildingDto = createStartNewBuildingDto();
        Town existingTown = createTown();
        //and
        QueuedBuilding expectedBuilding = createExpectedBuilding(startBuildingDto, existingTown);
        QueuedBuildingDTO expectedDto = createExpectedDto(expectedBuilding);
        //and
        given(mockTownRepo.findByIdAndUserUsername(anyLong(), any()))
                .willReturn(Optional.of(existingTown));
        //and
        given(mockQueueMapper.toDto(any()))
                .willReturn(expectedDto);
        //and
        TestTimeUtils.setUpMockTime(clock);

        //when
        QueuedBuildingDTO actual = underTest.enqueueNewBuilding(principalUsername, townId, startBuildingDto);

        //then
        ArgumentCaptor<QueuedBuilding> argCaptor = ArgumentCaptor.forClass(QueuedBuilding.class);
        verify(mockQueueMapper).toDto(argCaptor.capture());
        assertThat(argCaptor.getValue())
                .isEqualTo(expectedBuilding);
        //and
        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDto);
    }

    private StartNewBuildingDTO createStartNewBuildingDto() {
        return new StartNewBuildingDTO("Some Building", 10);
    }

    private Town createTown() {
        return new Town(
                "Some Town",
                100,
                Region.DESERT,
                null,
                new Resources(),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    private QueuedBuilding createExpectedBuilding(StartNewBuildingDTO startBuildingDto, Town town) {
        LocalDateTime constructionStart = TestTimeUtils.NOW.toLocalDateTime();
        LocalDateTime constructionEnd = TestTimeUtils.getTimePlusSeconds(10);
        Building building = new Building(
                startBuildingDto.getName(),
                startBuildingDto.getLevel(),
                "Some New Building",
                null
        );

        return new QueuedBuilding(
                building,
                town,
                new Resources(),
                constructionStart,
                constructionEnd
        );
    }

    private QueuedBuildingDTO createExpectedDto(QueuedBuilding queued) {
        QueuedBuildingMapperImpl mapper = new QueuedBuildingMapperImpl(
                new BuildingMapperImpl(),
                new ResourceMapperImpl()
        );
        return mapper.toDto(queued);
    }
}
