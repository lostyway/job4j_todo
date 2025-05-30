package ru.job4j.todo.exception;

public class UserModificationException extends RuntimeException {
    public UserModificationException(String message) {
        super(message);
    }
}
