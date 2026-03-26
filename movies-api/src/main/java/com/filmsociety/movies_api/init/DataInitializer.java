package com.filmsociety.movies_api.init;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.filmsociety.movies_api.entity.Actor;
import com.filmsociety.movies_api.entity.Genre;
import com.filmsociety.movies_api.entity.Movie;
import com.filmsociety.movies_api.repository.ActorRepository;
import com.filmsociety.movies_api.repository.GenreRepository;
import com.filmsociety.movies_api.repository.MovieRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public DataInitializer(GenreRepository genreRepository, ActorRepository actorRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    @Transactional
    public void initData() {
        // Clean up duplicate genres
        cleanupDuplicateGenres();

        // Clean up duplicate actors
        cleanupDuplicateActors();

        // Clean up duplicate movies
        cleanupDuplicateMovies();
    }

    private void cleanupDuplicateGenres() {
        genreRepository.findAll().stream()
                .collect(Collectors.groupingBy(Genre::getName)) // Group by name
                .values().stream()
                .filter(genres -> genres.size() > 1) // Find duplicates
                .forEach(duplicateGenres -> {
                    duplicateGenres.stream()
                            .skip(1) // Skip the first, keeping it
                            .forEach(genre -> genreRepository.delete(genre));
                });
    }

    private void cleanupDuplicateActors() {
        actorRepository.findAll().stream()
                .collect(Collectors.groupingBy(Actor::getName)) // Group by name
                .values().stream()
                .filter(actors -> actors.size() > 1) // Find duplicates
                .forEach(duplicateActors -> {
                    duplicateActors.stream()
                            .skip(1) // Skip the first, keeping it
                            .forEach(actor -> actorRepository.delete(actor));
                });
    }

    private void cleanupDuplicateMovies() {
        movieRepository.findAll().stream()
                .collect(Collectors.groupingBy(Movie::getTitle)) // Group by title
                .values().stream()
                .filter(movies -> movies.size() > 1) // Find duplicates
                .forEach(duplicateMovies -> {
                    duplicateMovies.stream()
                            .skip(1) // Skip the first, keeping it
                            .forEach(movie -> movieRepository.delete(movie));
                });
    }
}
