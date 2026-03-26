package com.filmsociety.movies_api.controller;

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

import com.filmsociety.movies_api.dto.GenreCreateDto;
import com.filmsociety.movies_api.dto.GenreResponseDto;
import com.filmsociety.movies_api.dto.GenreUpdateDto;
import com.filmsociety.movies_api.entity.Genre;
import com.filmsociety.movies_api.security.AuthContext;
import com.filmsociety.movies_api.service.GenreService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final ObjectProvider<AuthContext> authContextProvider;

    public GenreController(GenreService genreService, ObjectProvider<AuthContext> authContextProvider) {
        this.genreService = genreService;
        this.authContextProvider = authContextProvider;
    }

    @PostMapping
    public ResponseEntity<GenreResponseDto> createGenre(@RequestBody @Valid GenreCreateDto dto) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can create genres.");
        }
        GenreResponseDto created = genreService.createGenre(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // Set status to 201 Created
    }

    @PatchMapping("/{id}")
    public GenreResponseDto updateGenre(@PathVariable Long id, @RequestBody @Valid GenreUpdateDto updates) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can update genres.");
        }
        return genreService.updateGenre(id, updates);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id, @RequestParam(required = false) boolean force) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can delete genres.");
        }
        genreService.deleteGenre(id, force);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<GenreResponseDto> getGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }

        Pageable pageable = PageRequest.of(page, size);
        return genreService.getGenres(name, pageable);  // Already returns Page<GenreResponseDto>
    }

    @GetMapping("/{id}")
    public GenreResponseDto getGenreById(@PathVariable Long id) {
        Genre genre = genreService.getGenreById(id);
        return GenreResponseDto.fromEntity(genre);
    }
}
