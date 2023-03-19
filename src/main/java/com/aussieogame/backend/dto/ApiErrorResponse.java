package com.aussieogame.backend.dto;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class ApiErrorResponse {
    String error;

    public static ApiErrorResponse from(@NonNull String error) {
        return new ApiErrorResponse(error);
    }
}
