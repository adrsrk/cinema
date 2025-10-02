package com.app.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(Long id) {
        super("Сеанс с id " + id + " не найден!");
    }
}
