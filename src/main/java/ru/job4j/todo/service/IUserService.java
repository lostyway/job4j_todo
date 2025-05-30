package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

public interface IUserService {
    User save(User user);

    User findById(int id);

    User findByLoginAndPassword(String login, String password);

    boolean isLoginNameAvailable(String loginName);
}
