package com.aussieogame.backend.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.BDDMockito.given;

public class TestTimeUtils {
    public static final ZonedDateTime NOW = ZonedDateTime.of(
            2000,
            11,
            11,
            11,
            11,
            11,
            0,
            ZoneId.of("GMT")
    );

    public static void setUpMockTime(Clock clock) {
        given(clock.getZone())
                .willReturn(NOW.getZone());
        given(clock.instant())
                .willReturn(NOW.toInstant());
    }

    public static LocalDateTime getTimeMinusSeconds(long seconds) {
        return NOW.minusSeconds(seconds).toLocalDateTime();
    }

    public static LocalDateTime getTimePlusSeconds(long seconds) {
        return NOW.plusSeconds(seconds).toLocalDateTime();
    }
}
