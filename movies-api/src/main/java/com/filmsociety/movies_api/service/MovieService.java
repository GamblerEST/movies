package com.filmsociety.movies_api.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filmsociety.movies_api.dto.ActorSummaryDto;
import com.filmsociety.movies_api.dto.GenreSummaryDto;
import com.filmsociety.movies_api.dto.MovieCreateDto;
import com.filmsociety.movies_api.dto.MovieResponseDto;
import com.filmsociety.movies_api.dto.MovieUpdateDto;
import com.filmsociety.movies_api.entity.Actor;
import com.filmsociety.movies_api.entity.Genre;
import com.filmsociety.movies_api.entity.Movie;
import com.filmsociety.movies_api.exception.ResourceNotFoundException;
import com.filmsociety.movies_api.repository.ActorRepository;
import com.filmsociety.movies_api.repository.GenreRepository;
import com.filmsociety.movies_api.repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository,
            GenreRepository genreRepository,
            ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
    }

    public MovieResponseDto createMovie(MovieCreateDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle().trim());
        movie.setDescription(dto.getDescription());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDuration(dto.getDuration());

        // Handle genres
        if (dto.getGenreIds() != null) {
            Set<Genre> genres = dto.getGenreIds().stream()
                    .map(genreId -> genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", genreId)))
                    .collect(Collectors.toSet());
            genres.forEach(movie::addGenre);
        }

        // Handle actors
        if (dto.getActorIds() != null) {
            Set<Actor> actors = dto.getActorIds().stream()
                    .map(actorId -> actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId)))
                    .collect(Collectors.toSet());
            actors.forEach(movie::addActor);
        }

        Movie saved = movieRepository.save(movie);
        return MovieResponseDto.fromEntity(saved); // Returning MovieResponseDto
    }

    @Transactional(readOnly = true)
    public MovieResponseDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        return MovieResponseDto.fromEntity(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieResponseDto> getMovies(Long genreId, Long actorId, Integer year, Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findByFilters(genreId, actorId, year, pageable);

        return moviePage.map(movie -> {
            MovieResponseDto dto = MovieResponseDto.fromEntity(movie);
            dto.setActors(movie.getActors().stream()
                    .map(ActorSummaryDto::fromEntity)
                    .collect(Collectors.toSet()));
            dto.setGenres(movie.getGenres().stream()
                    .map(GenreSummaryDto::fromEntity)
                    .collect(Collectors.toSet()));
            return dto; // Returning DTO with actors and genres
        });
    }

    @Transactional(readOnly = true)
    public Set<MovieResponseDto> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title.trim()).stream()
                .map(MovieResponseDto::fromEntity)
                .collect(Collectors.toSet()); // Returning DTOs
    }

    @Transactional
    public MovieResponseDto addActorToMovie(Long movieId, Actor actor) {
        // Fetch the movie
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", movieId));

        // Add the actor to the movie (ensure the actor is not already added)
        if (!movie.getActors().contains(actor)) {
            movie.addActor(actor);
        }

        // Save the movie and return the updated movie DTO
        movieRepository.save(movie);
        return MovieResponseDto.fromEntity(movie);
    }

    @Transactional
    public MovieResponseDto updateActorsInMovie(Long id, MovieUpdateDto updates) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        // Handle actors - clear the current actors and add the new ones
        movie.getActors().clear(); // This removes all actors from the movie
        if (updates.getActorIds() != null) {
            Set<Actor> actors = updates.getActorIds().stream()
                    .map(actorId -> actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId)))
                    .collect(Collectors.toSet());
            actors.forEach(movie::addActor); // Adding new actors to the movie
        }

        Movie saved = movieRepository.save(movie);
        return MovieResponseDto.fromEntity(saved); // Returning updated MovieResponseDto
    }

    public MovieResponseDto updateMovie(Long id, MovieUpdateDto updates) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));

        if (updates.getTitle() != null) {
            movie.setTitle(updates.getTitle().trim());
        }
        if (updates.getDescription() != null) {
            movie.setDescription(updates.getDescription());
        }
        if (updates.getReleaseYear() != null) {
            movie.setReleaseYear(updates.getReleaseYear());
        }
        if (updates.getDuration() != null) {
            movie.setDuration(updates.getDuration());
        }

        // Handle genres
        if (updates.getGenreIds() != null) {
            movie.getGenres().clear();
            Set<Genre> genres = updates.getGenreIds().stream()
                    .map(genreId -> genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre", "id", genreId)))
                    .collect(Collectors.toSet());
            genres.forEach(movie::addGenre);
        }

        // Handle actors
        if (updates.getActorIds() != null) {
            movie.getActors().clear();
            Set<Actor> actors = updates.getActorIds().stream()
                    .map(actorId -> actorRepository.findById(actorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId)))
                    .collect(Collectors.toSet());
            actors.forEach(movie::addActor);
        }

        Movie saved = movieRepository.save(movie);
        return MovieResponseDto.fromEntity(saved); // Returning updated DTO
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "id", id));
        if (!movie.getActors().isEmpty() || !movie.getGenres().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete movie with associated actors or genres.");
        }
        movieRepository.delete(movie); // Deleting movie
    }
}
