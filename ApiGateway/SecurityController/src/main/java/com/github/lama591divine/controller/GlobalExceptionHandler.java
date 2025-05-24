package com.github.lama591divine.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientErr(HttpClientErrorException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of("error", ex.getStatusText()));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleServerErr(HttpServerErrorException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Bank core is unavailable"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Unexpected gateway error"));
    }
}
