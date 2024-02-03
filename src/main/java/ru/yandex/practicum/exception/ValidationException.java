package ru.yandex.practicum.exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("Not valid!");
    }
}
