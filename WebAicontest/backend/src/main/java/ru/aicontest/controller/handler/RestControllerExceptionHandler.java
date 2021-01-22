package ru.aicontest.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.aicontest.exception.NoSuchManagerException;
import ru.aicontest.exception.NoSuchResourceException;
import ru.aicontest.exception.ValidationException;

@ControllerAdvice
public class RestControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchResourceException.class)
    public void handleNoSuchResourceException() {
        // Nothing to do
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchManagerException.class)
    public ResponseEntity<Object> handleManagerException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException() {
        // Nothing to do
    }
}
