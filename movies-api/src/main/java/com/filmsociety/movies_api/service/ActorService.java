package com.filmsociety.movies_api.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filmsociety.movies_api.dto.ActorCreateDto;
import com.filmsociety.movies_api.dto.ActorResponseDto;
import com.filmsociety.movies_api.dto.ActorUpdateDto;
import com.filmsociety.movies_api.entity.Actor;
import com.filmsociety.movies_api.exception.DuplicateActorException;
import com.filmsociety.movies_api.exception.InvalidDateFormatException;
import com.filmsociety.movies_api.exception.ResourceNotFoundException;
import com.filmsociety.movies_api.repository.ActorRepository;

@Service
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Transactional
    public ActorResponseDto createActor(ActorCreateDto dto) {
        // Validate that the birthDate format is correct (yyyy-MM-dd) before parsing
        String birthDateString = dto.getBirthDate();

        // Manually validate the month (1-12) and day (valid for that month)
        if (!birthDateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new InvalidDateFormatException("Invalid date format. Expected: yyyy-MM-dd");
        }

        String[] dateParts = birthDateString.split("-");
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        // Check if the month is valid
        if (month < 1 || month > 12) {
            throw new InvalidDateFormatException("Invalid month in the birth date.");
        }

        // Check if the day is valid for the given month
        try {
            LocalDate parsedDate = LocalDate.parse(birthDateString);  // This will throw if day is invalid
            if (parsedDate.getDayOfMonth() != day) {
                throw new InvalidDateFormatException("Invalid day in the birth date.");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid day in the birth date.");
        }

        // Check if actor with the same name and birth date exists
        Actor existingActor = actorRepository.findByNameAndBirthDate(dto.getName(), LocalDate.parse(birthDateString));

        if (existingActor != null) {
            throw new DuplicateActorException("Actor with the same name and birth date already exists.");
        }

        // Create the new actor
        Actor actor = new Actor();
        actor.setName(dto.getName());
        actor.setBio(dto.getBio());

        // Now parse the valid birth date and set it
        LocalDate birthDate = LocalDate.parse(birthDateString);
        actor.setBirthDate(birthDate);

        actor = actorRepository.save(actor);
        return ActorResponseDto.fromEntity(actor);
    }

    public Page<ActorResponseDto> getDistinctActors(Pageable pageable) {
        Page<Actor> actors = actorRepository.findDistinctActors(pageable);
        return actors.map(ActorResponseDto::fromEntity); // Returns DTOs
    }

    public Page<ActorResponseDto> getActors(Pageable pageable) {
        Page<Actor> actors = actorRepository.findAll(pageable);
        return actors.map(ActorResponseDto::fromEntity); // Returns DTOs
    }

    public Page<ActorResponseDto> getActors(String name, Pageable pageable) {
        Page<Actor> actors = actorRepository.findByNameContainingIgnoreCase(name, pageable);
        return actors.map(ActorResponseDto::fromEntity); // Returns DTOs
    }

    public ActorResponseDto getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", id));
        return ActorResponseDto.fromEntity(actor); // Returns DTO
    }

    @Transactional
    public ActorResponseDto updateActor(Long id, ActorUpdateDto updates) {
        // Find the actor by id
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", id));

        // Update name and bio if provided
        if (updates.getName() != null) {
            actor.setName(updates.getName());
        }
        if (updates.getBio() != null) {
            actor.setBio(updates.getBio());
        }

        // Update birth date if provided
        if (updates.getBirthDate() != null) {
            String birthDateString = updates.getBirthDate();  // birthDate is now a String

            // Manually validate the month (1-12) and day (valid for that month)
            if (!birthDateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                throw new InvalidDateFormatException("Invalid date format. Expected: yyyy-MM-dd");
            }

            String[] dateParts = birthDateString.split("-");
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);

            // Check if the month is valid
            if (month < 1 || month > 12) {
                throw new InvalidDateFormatException("Invalid month in the birth date.");
            }

            // Check if the day is valid for the given month
            try {
                LocalDate parsedDate = LocalDate.parse(birthDateString);  // This will throw if day is invalid
                if (parsedDate.getDayOfMonth() != day) {
                    throw new InvalidDateFormatException("Invalid day in the birth date.");
                }

                // Update the birth date
                actor.setBirthDate(parsedDate);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException("Invalid day in the birth date.");
            }
        }

        // Save and return the updated actor
        actor = actorRepository.save(actor);
        return ActorResponseDto.fromEntity(actor);  // Return updated DTO
    }

    public void deleteActor(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", id));

        actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
        actor.getMovies().clear();

        actorRepository.delete(actor); // Deletes actor
    }

    public boolean canDeleteWithoutForce(Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
        return actor.getMovies().isEmpty();  // Check if actor can be deleted without force
    }
}
