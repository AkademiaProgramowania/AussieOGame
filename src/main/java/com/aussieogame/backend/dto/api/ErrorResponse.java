package com.aussieogame.backend.dto.api;


public record ErrorResponse(String error) implements ApiResponse {
    public static ErrorResponse from(String error) {
        return new ErrorResponse(error);
    }
}
