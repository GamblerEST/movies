package com.filmsociety.movies_api.dto;

public class GenreResponseDto {

    private Long id;
    private String name;

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

    public static GenreResponseDto fromEntity(com.filmsociety.movies_api.entity.Genre genre) {
        GenreResponseDto dto = new GenreResponseDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
