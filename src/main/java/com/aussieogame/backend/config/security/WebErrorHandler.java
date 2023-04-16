package com.aussieogame.backend.config.security;

import com.aussieogame.backend.model.dto.api.ApiResponse;
import com.aussieogame.backend.model.dto.api.mapper.SimpleResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebErrorHandler {

    private final SimpleResponseFactory responseFactory;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFound() {
        return responseFactory.notFound("No handler found for this request.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleForbidden() {
        return responseFactory.error("Access denied.", HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse> handleInternalServerError(Exception exception) {
        return handleException(exception);
    }

    private ResponseEntity<ApiResponse> handleException(Exception exception) {
        String errorMsg = "500 INTERNAL SERVER ERROR. " +
                "Exception: " +
                exception.getClass() +
                " Message:   " +
                exception.getMessage();

        return responseFactory.error(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
