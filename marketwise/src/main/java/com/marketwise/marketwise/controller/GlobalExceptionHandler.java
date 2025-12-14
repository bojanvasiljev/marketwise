package com.marketwise.marketwise.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.marketwise.marketwise.model.ErrorResponse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
    return buildResponse(HttpStatus.BAD_REQUEST, "INVALID_INPUT", ex.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Object> handleConflict(IllegalStateException ex) {
    return buildResponse(HttpStatus.CONFLICT, "CONFLICT", ex.getMessage());
  }

  // @ExceptionHandler(Exception.class)
  // public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
  //   return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error");
  // }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
    logger.error("Unexpected error occurred", ex);

    ErrorResponse response = new ErrorResponse(
        "INTERNAL_ERROR",
        "Unexpected error",
        LocalDateTime.now()
    );
    
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  private ResponseEntity<Object> buildResponse(HttpStatus status, String errorCode, String message) {
    return ResponseEntity.status(status)
        .body(Map.of(
            "timestamp", Instant.now().toString(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "errorCode", errorCode,
            "message", message));
  }
}
