package com.filmsociety.movies_api.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.filmsociety.movies_api.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Check if an actor exists by name, case insensitive
    boolean existsByNameIgnoreCase(String name);

    // Find actors by name containing the search term, ignoring case and with pagination
    Page<Actor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Use DISTINCT to avoid duplicates in the result set
    @Query("SELECT DISTINCT a FROM Actor a ORDER BY a.name")
    Page<Actor> findDistinctActors(Pageable pageable);

    // Custom query method to find an actor by name and birth date
    Actor findByNameAndBirthDate(String name, LocalDate birthDate);

}
