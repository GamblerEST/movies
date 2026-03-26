package com.filmsociety.movies_api.controller;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filmsociety.movies_api.dto.ActorCreateDto;
import com.filmsociety.movies_api.dto.ActorResponseDto;
import com.filmsociety.movies_api.dto.ActorUpdateDto;
import com.filmsociety.movies_api.security.AuthContext;
import com.filmsociety.movies_api.service.ActorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;
    private final ObjectProvider<AuthContext> authContextProvider;

    public ActorController(ActorService actorService, ObjectProvider<AuthContext> authContextProvider) {
        this.actorService = actorService;
        this.authContextProvider = authContextProvider;
    }

    @PostMapping
    public ResponseEntity<ActorResponseDto> createActor(@RequestBody @Valid ActorCreateDto request) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can create actors.");
        }
        ActorResponseDto created = actorService.createActor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // Set status to 201 Created
    }

    @PatchMapping("/{id}")
    public ActorResponseDto patchActor(@PathVariable Long id, @RequestBody ActorUpdateDto updates) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can update actors.");
        }
        return actorService.updateActor(id, updates);  // Service returns DTO
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id, @RequestParam(required = false) boolean force) {
        if (!authContextProvider.getObject().isAdmin()) {
            throw new SecurityException("Only admins can delete actors.");
        }
        if (!force && !actorService.canDeleteWithoutForce(id)) {
            throw new IllegalArgumentException("Cannot delete actor with associated movies unless force=true");
        }
        actorService.deleteActor(id);  // Service handles deletion
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<ActorResponseDto> getActors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {

        Pageable pageable = PageRequest.of(page, size);
        return (name == null || name.isBlank())
                ? actorService.getActors(pageable)
                : actorService.getActors(name, pageable);
    }

    @GetMapping("/{id}")
    public ActorResponseDto getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);  // Returns DTO from service
    }

    @GetMapping("/distinct")
    public Page<ActorResponseDto> getDistinctActors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return actorService.getDistinctActors(pageable);  // Returns DTOs
    }
}
