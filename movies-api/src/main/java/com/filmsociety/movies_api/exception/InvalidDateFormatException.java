package com.filmsociety.movies_api.exception;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
