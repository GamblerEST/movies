package com.filmsociety.movies_api.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.filmsociety.movies_api.entity.Movie;

public class MovieResponseDto {

    private Long id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer duration;

    private Set<ActorSummaryDto> actors;
    private Set<GenreSummaryDto> genres;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<ActorSummaryDto> getActors() {
        return actors;
    }

    public void setActors(Set<ActorSummaryDto> actors) {
        this.actors = actors;
    }

    public Set<GenreSummaryDto> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreSummaryDto> genres) {
        this.genres = genres;
    }

    public static MovieResponseDto fromEntity(Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDuration(movie.getDuration());

        // Ensure no duplicate actors in the response
        if (movie.getActors() != null) {
            dto.setActors(movie.getActors().stream()
                    .map(ActorSummaryDto::fromEntity)
                    .collect(Collectors.toSet()));  // Using Set to ensure uniqueness
        }

        // Ensure no duplicate genres in the response
        if (movie.getGenres() != null) {
            dto.setGenres(movie.getGenres().stream()
                    .map(GenreSummaryDto::fromEntity)
                    .collect(Collectors.toSet()));  // Using Set to ensure uniqueness
        }

        return dto;
    }
}
