package org.user.infraestructor.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> handleUserException(UserException e) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .message(e.getMessage()).build(), e.getStatus());
    }
    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(AttributeException e) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .message(e.getMessage()).build(), e.getStatus());
    }
}