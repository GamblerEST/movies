package com.filmsociety.movies_api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.filmsociety.movies_api.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Fetch movies that have the given genreId in their genres
    Page<Movie> findByGenresId(Long genreId, Pageable pageable);

    Page<Movie> findByReleaseYear(Integer releaseYear, Pageable pageable);

    Page<Movie> findByActors_Id(Long actorId, Pageable pageable);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    @EntityGraph(attributePaths = {"actors", "genres"})
    @Query("SELECT DISTINCT m FROM Movie m "
            + "LEFT JOIN FETCH m.actors a "
            + "LEFT JOIN FETCH m.genres g "
            + "WHERE (:genreId IS NULL OR g.id = :genreId) "
            + "AND (:actorId IS NULL OR a.id = :actorId) "
            + "AND (:year IS NULL OR m.releaseYear = :year)")
    Page<Movie> findByFilters(@Param("genreId") Long genreId,
            @Param("actorId") Long actorId,
            @Param("year") Integer releaseYear,
            Pageable pageable);
}
