package com.filmsociety.movies_api.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required and cannot be empty.")
    @Size(max = 255)
    private String title;

    @Column(length = 2000)
    private String description;

    @NotNull(message = "Release year is required and cannot be null.")
    @Min(1895)
    @Max(2030)
    private Integer releaseYear;

    @NotNull(message = "Duration is required and cannot be null.")
    @Min(1)
    @Max(600)
    private Integer duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnoreProperties("movies") // prevent recursion when serializing Genre
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @JsonIgnoreProperties("movies") // prevent recursion when serializing Actor
    private Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(Long id, String title, String description, Integer releaseYear, Integer duration,
            Set<Genre> genres, Set<Actor> actors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
        if (genres != null) {
            this.genres.addAll(genres);
        }
        if (actors != null) {
            this.actors.addAll(actors);
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {  // add setter for id, useful for in-memory creation
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

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    // Convenience methods to keep bidirectional consistency
    public void addGenre(Genre genre) {
        if (genres.add(genre)) {
            genre.getMovies().add(this);
        }
    }

    public void removeGenre(Genre genre) {
        if (genres.remove(genre)) {
            genre.getMovies().remove(this);
        }
    }

    public void addActor(Actor actor) {
        if (actors.add(actor)) {
            actor.getMovies().add(this);
        }
    }

    public void removeActor(Actor actor) {
        if (actors.remove(actor)) {
            actor.getMovies().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
