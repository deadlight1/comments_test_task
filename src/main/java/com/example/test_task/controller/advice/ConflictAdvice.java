package com.example.test_task.controller.advice;

import com.example.test_task.exception.CantCreateCommentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestControllerAdvice
public class ConflictAdvice {


    @ExceptionHandler(CantCreateCommentException.class)
    public ResponseEntity<?> conflict(CantCreateCommentException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(CONFLICT).contentType(MediaType.APPLICATION_JSON).body(e.getCommentDto());
    }
}
