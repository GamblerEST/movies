package com.filmsociety.movies_api.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "genres", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated

    @NotBlank(message = "Name is required and cannot be blank.")
    @Size(min = 2, max = 50)
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private Set<Movie> movies = new HashSet<>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    // No setter for ID needed as it is auto-generated
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    // Convenience methods to keep bidirectional consistency
    public void addMovie(Movie movie) {
        if (movies.add(movie)) {
            movie.getGenres().add(this);
        }
    }

    public void removeMovie(Movie movie) {
        if (movies.remove(movie)) {
            movie.getGenres().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genre)) {
            return false;
        }
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
