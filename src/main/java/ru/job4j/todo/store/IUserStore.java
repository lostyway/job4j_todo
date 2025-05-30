package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

import java.util.Optional;

public interface IUserStore {
    Optional<User> save(User user);

    Optional<User> findById(int id);

    Optional<User> findByLoginAndPassword(String login, String password);

    boolean isLoginNameAvailable(String loginName);
}
