package com.filmsociety.movies_api.exception;

public class DuplicateActorException extends RuntimeException {

    public DuplicateActorException(String message) {
        super(message);  // Call the parent class constructor with the message
    }
}
