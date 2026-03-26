package com.filmsociety.movies_api.dto;

public class GenreSummaryDto {

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

    // Static method to convert Genre entity to GenreSummaryDto
    public static GenreSummaryDto fromEntity(com.filmsociety.movies_api.entity.Genre genre) {
        GenreSummaryDto dto = new GenreSummaryDto();
        if (genre != null) {
            dto.setId(genre.getId());
            dto.setName(genre.getName());
        }
        return dto;
    }
}
