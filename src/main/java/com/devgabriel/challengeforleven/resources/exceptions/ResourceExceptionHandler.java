package com.devgabriel.challengeforleven.resources.exceptions;

import com.devgabriel.challengeforleven.services.exceptions.DatabaseException;
import com.devgabriel.challengeforleven.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandardError error = createStandardError(status, e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> integrityViolation(DatabaseException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    StandardError error = createStandardError(status, e.getMessage(), request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  private StandardError createStandardError(HttpStatus status, String error, String requestUri) {
    return new StandardError(Instant.now(), status.value(), error, "Resource not found", requestUri);
  }
}
