package com.filmsociety.movies_api.controller;

import java.util.Set;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filmsociety.movies_api.dto.ActorSummaryDto;
import com.filmsociety.movies_api.dto.MovieCreateDto;
import com.filmsociety.movies_api.dto.MovieResponseDto;
import com.filmsociety.movies_api.dto.MovieUpdateDto;
import com.filmsociety.movies_api.entity.Actor;
import com.filmsociety.movies_api.security.AuthContext;
import com.filmsociety.movies_api.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ObjectProvider<AuthContext> authContextProvider;

    public MovieController(MovieService movieService, ObjectProvider<AuthContext> authContextProvider) {
        this.movieService = movieService;
        this.authContextProvider = authContextProvider;
    }

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@RequestBody @Valid MovieCreateDto dto) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can create movies.");
        }
        MovieResponseDto created = movieService.createMovie(dto);  // Service returns DTO
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}")
    public MovieResponseDto updateMovie(@PathVariable Long id,
            @RequestBody @Valid MovieUpdateDto updates) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can update movies.");
        }
        return movieService.updateMovie(id, updates);  // Service returns DTO
    }

    @GetMapping
    public Page<MovieResponseDto> getMovies(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }

        Pageable pageable = PageRequest.of(page, size);
        return movieService.getMovies(genreId, actorId, year, pageable);  // Service returns DTOs
    }

    @GetMapping("/{id}")
    public MovieResponseDto getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can delete movies.");
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{movieId}/actors")
    public ResponseEntity<MovieResponseDto> addActorToMovie(@PathVariable Long movieId, @RequestBody Actor actor) {
        MovieResponseDto updatedMovie = movieService.addActorToMovie(movieId, actor);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
    }

    @GetMapping("/{movieId}/actors")
    public ResponseEntity<Set<ActorSummaryDto>> getActorsForMovie(@PathVariable Long movieId) {
        // Call a service method to get the movie by ID
        MovieResponseDto movie = movieService.getMovieById(movieId);

        // Return the actors for the movie as a set of ActorSummaryDto
        return ResponseEntity.ok(movie.getActors());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponseDto> updateActorsInMovie(@PathVariable Long id, @RequestBody MovieUpdateDto updates) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can update movies.");
        }

        MovieResponseDto updatedMovie = movieService.updateActorsInMovie(id, updates); // Service method to update actors
        return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
    }

}
