package org.user.application.exception;

import org.springframework.http.HttpStatus;

public class AttributeException extends RuntimeException {

    private final HttpStatus status;

    public AttributeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
