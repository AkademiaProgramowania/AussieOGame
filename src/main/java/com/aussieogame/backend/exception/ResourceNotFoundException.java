package com.aussieogame.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(long id) {
        super("""
                No town found
                belonging to the current user
                and with ID %d.
                """.formatted(id));
    }
}
