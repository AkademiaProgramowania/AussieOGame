package com.aussieogame.backend.dto.api.mapper;

import com.aussieogame.backend.dto.api.ApiResponse;
import com.aussieogame.backend.dto.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseFactory<T> {
    ResponseEntity<ApiResponse> ok(T data);
    ResponseEntity<ApiResponse> created(T data);
    default ResponseEntity<ApiResponse> notFound(T data) {
        return error(data, HttpStatus.NOT_FOUND);
    }
    default ResponseEntity<ApiResponse> error(T data, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(data.toString()), status);
    }
}
