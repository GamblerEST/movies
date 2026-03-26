package com.filmsociety.movies_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.filmsociety.movies_api.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    // Add this method to check if a genre exists by name, case insensitive
    boolean existsByNameIgnoreCase(String name);

    // Paginated search of genres by name with case-insensitive search
    Page<Genre> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Fetch genres distinct by their IDs and apply sorting
    @Query("SELECT g FROM Genre g ORDER BY g.name ASC")
    Page<Genre> findDistinctGenres(Pageable pageable);
}
