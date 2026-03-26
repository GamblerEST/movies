package com.filmsociety.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;

public class ActorUpdateDto {

    private String name;

    @JsonProperty("birth_date")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid birthDate format. Expected: yyyy-MM-dd")
    private String birthDate;  // Store birth date as String for validation

    private String bio;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
