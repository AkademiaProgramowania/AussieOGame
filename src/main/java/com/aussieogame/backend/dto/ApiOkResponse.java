package com.aussieogame.backend.dto;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class ApiOkResponse implements ApiResponse {
    String data;

    public static ApiOkResponse from(@NonNull String data) {
        return new ApiOkResponse(data);
    }
}
