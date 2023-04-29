package com.aussieogame.backend.exception;

public class RequestedActionNotAllowedException extends RuntimeException {
    public RequestedActionNotAllowedException(String message) {
        super(message);
    }
}
