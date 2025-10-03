package com.app.exception;

public class InvalidSeatPositionException extends RuntimeException {
    public InvalidSeatPositionException(String message) {
        super(message);
    }
}
