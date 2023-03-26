package com.aussieogame.backend.dto.api.mapper;

import com.aussieogame.backend.dto.api.ApiResponse;
import com.aussieogame.backend.dto.api.SimpleStringReponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SimpleResponseFactory implements ResponseFactory<String> {

    @Override
    public ResponseEntity<ApiResponse> ok(String data) {
        return new ResponseEntity<>(new SimpleStringReponse(data), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse> created(String data) {
        return new ResponseEntity<>(new SimpleStringReponse(data), HttpStatus.CREATED);
    }
}
