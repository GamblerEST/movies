package com.filmsociety.movies_api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> welcome() {
        return Map.of(
                "message", "Welcome to the Movies API! -- Mikk Merila",
                "version", "1.0.0",
                "endpoints", Map.of(
                        "movies", "/api/movies",
                        "actors", "/api/actors",
                        "genres", "/api/genres"
                ),
                "documentation", "Visit the endpoints above"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "Movies API"
        );
    }
}
