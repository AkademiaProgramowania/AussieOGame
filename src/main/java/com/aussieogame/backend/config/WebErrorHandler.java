package com.aussieogame.backend.config;

import com.aussieogame.backend.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class WebErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiErrorResponse handleNotFound(HttpServletRequest request, Exception exception) {
        return handleException("404 NOT FOUND");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ApiErrorResponse handleForbidden(HttpServletRequest request, Exception exception) {
        return handleException("403 FORBIDDEN");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ApiErrorResponse handleInternalServerError(HttpServletRequest request, Exception exception) {
        return handleException(exception);
    }

    private ApiErrorResponse handleException(Exception exception) {
        String errorMsg = "500 INTERNAL SERVER ERROR" +
                "\n\tException: " +
                exception.getClass() +
                "\n\tMessage:   " +
                exception.getMessage();

        return handleException(errorMsg);
    }

    private ApiErrorResponse handleException(String errorMsg) {
        return ApiErrorResponse.from(errorMsg);
    }
}
