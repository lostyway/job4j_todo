package ru.job4j.todo.exception;

public class TaskUpdateException extends RuntimeException {
    public TaskUpdateException(String message) {
        super(message);
    }
}
