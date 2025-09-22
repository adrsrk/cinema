package com.app.exception;

public class HallNotFoundException extends RuntimeException {

    public HallNotFoundException(Long id) {
        super("Зал с id " + id + " не найден!");
    }
}
