package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.todo.exception.UserModificationException;
import ru.job4j.todo.exception.UserNotFoundException;
import ru.job4j.todo.exception.UserWithSameLoginAlreadyExist;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserStore userStore;

    @Override
    public User save(User user) {
        if (!isLoginNameAvailable(user.getLogin())) {
            throw new UserWithSameLoginAlreadyExist("Логин уже занят.Придумайте другой");
        }
        return userStore.save(user)
                .orElseThrow(() -> new UserModificationException("Не удалось создать профиль"));
    }

    @Override
    public User findById(int id) {
        return userStore.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Не удалось найти пользователя с id: " + id));
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Логин и пароль не могут быть пустыми!");
        }
        return userStore.findByLoginAndPassword(login, password)
                .orElseThrow(() -> new UserNotFoundException("Не удалось найти пользователя с таким логином и паролем"));
    }

    @Override
    public boolean isLoginNameAvailable(String loginName) {
        return userStore.isLoginNameAvailable(loginName);
    }
}
