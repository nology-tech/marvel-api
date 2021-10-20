package com.nology.charactersAPI.exceptions;

public class MarvelApiException extends Exception {
    public MarvelApiException(String errorMessage) {
        super(errorMessage);
    }
}
