package com.filmsociety.movies_api.dto;

import java.util.Objects;

public class ActorSummaryDto {

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

    // Override equals and hashCode to ensure proper deduplication in Set
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActorSummaryDto that = (ActorSummaryDto) o;
        return id != null && id.equals(that.id); // ID is the key for comparison
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // ID is used in hashCode for proper deduplication
    }

    // Static method to convert Actor entity to ActorSummaryDto
    public static ActorSummaryDto fromEntity(com.filmsociety.movies_api.entity.Actor actor) {
        ActorSummaryDto dto = new ActorSummaryDto();
        if (actor != null) {
            dto.setId(actor.getId());
            dto.setName(actor.getName());
        }
        return dto;
    }
}
