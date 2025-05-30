package ru.job4j.todo.exception;

public class UserWithSameLoginAlreadyExist extends RuntimeException {
    public UserWithSameLoginAlreadyExist(String message) {
        super(message);
    }
}
