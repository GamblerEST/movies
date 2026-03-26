package com.filmsociety.movies_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ActorCreateDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @JsonProperty("birth_date")
    @NotBlank(message = "Birth date cannot be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid birthDate format. Expected: yyyy-MM-dd")
    private String birthDate;  // Keep as String for pattern validation

    private String bio;

    // Getters and Setters
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
