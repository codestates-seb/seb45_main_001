package com.sundayCinema.sundayCinema.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(DuplicateCommentException.class)
    public ResponseEntity<Object> handleDuplicateCommentException(DuplicateCommentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

