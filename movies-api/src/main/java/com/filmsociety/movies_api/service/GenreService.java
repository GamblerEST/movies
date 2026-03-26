package com.filmsociety.movies_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.filmsociety.movies_api.dto.GenreCreateDto;
import com.filmsociety.movies_api.dto.GenreResponseDto;
import com.filmsociety.movies_api.dto.GenreUpdateDto;
import com.filmsociety.movies_api.entity.Genre;
import com.filmsociety.movies_api.exception.ResourceNotFoundException;
import com.filmsociety.movies_api.repository.GenreRepository;
import com.filmsociety.movies_api.repository.MovieRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    // Get genre by id and return GenreResponseDto
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));
    }

    // Get all genres with optional name filtering and return Page<GenreResponseDto>
    public Page<GenreResponseDto> getGenres(String name, Pageable pageable) {
        Page<Genre> genres;
        if (name == null || name.isBlank()) {
            genres = genreRepository.findAll(pageable);  // Get all genres
        } else {
            genres = genreRepository.findByNameContainingIgnoreCase(name.trim(), pageable); // Get genres by name filter
        }
        // Convert to Page<GenreResponseDto>
        return genres.map(GenreResponseDto::fromEntity);
    }

    // Create a new genre
    public GenreResponseDto createGenre(GenreCreateDto dto) {
        String trimmedName = dto.getName().trim();
        if (genreRepository.existsByNameIgnoreCase(trimmedName)) {
            throw new IllegalArgumentException("Genre with this name already exists.");
        }

        Genre genre = new Genre();
        genre.setName(trimmedName);

        Genre saved = genreRepository.save(genre);
        return GenreResponseDto.fromEntity(saved);  // Return the created genre as DTO
    }

    // Update an existing genre
    public GenreResponseDto updateGenre(Long id, GenreUpdateDto updates) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));

        if (updates.getName() != null && !updates.getName().isBlank()) {
            String trimmedName = updates.getName().trim();
            if (genreRepository.existsByNameIgnoreCase(trimmedName)
                    && !trimmedName.equalsIgnoreCase(genre.getName())) {
                throw new IllegalArgumentException("Another genre with this name already exists.");
            }
            genre.setName(trimmedName);  // Set the updated name
        }

        Genre saved = genreRepository.save(genre);
        return GenreResponseDto.fromEntity(saved);  // Return the updated genre as DTO
    }

    public void deleteGenre(Long id, boolean force) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", id));

        if (force) {
            // If force is true, delete the genre even if there are associated movies
            movieRepository.findAll().forEach(movie -> {
                if (movie.getGenres().contains(genre)) {
                    movie.removeGenre(genre);  // Remove the genre from each movie
                }
            });

            genreRepository.delete(genre);  // Delete the genre
        } else {
            // Check if the genre has any associated movies
            if (!genre.getMovies().isEmpty()) {
                throw new IllegalArgumentException("Cannot delete genre with associated movies unless force=true");
            }
            genreRepository.delete(genre);  // Delete the genre if no associated movies
        }
    }
}
