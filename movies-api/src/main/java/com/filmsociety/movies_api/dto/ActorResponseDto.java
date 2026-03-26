package com.filmsociety.movies_api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.filmsociety.movies_api.entity.Actor; // For handling date format

public class ActorResponseDto {

    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Ensures date is formatted
    private LocalDate birthDate;

    private String bio;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Conversion method for entity to DTO
    public static ActorResponseDto fromEntity(Actor actor) {
        ActorResponseDto dto = new ActorResponseDto();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setBirthDate(actor.getBirthDate());
        dto.setBio(actor.getBio());
        return dto;
    }
}
