package com.filmsociety.movies_api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExceptionResponseBuilder {

    private ExceptionResponseBuilder() {
        // Prevent instantiation
    }

    public static ResponseEntity<Map<String, Object>> build(Exception ex, HttpStatus status, HttpServletRequest request) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", ex.getMessage());
        error.put("path", request.getRequestURI());
        error.put("exception", ex.getClass().getSimpleName());
        return new ResponseEntity<>(error, status);
    }
}
