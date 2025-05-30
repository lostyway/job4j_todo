package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class UserStore implements IUserStore {
    private final TransactionUtility tx;

    @Override
    public Optional<User> save(User user) {
        log.debug("save user {}", user);
        return Optional.ofNullable(tx.txResult(session -> {
            session.save(user);
            return user;
        }));
    }

    @Override
    public Optional<User> findById(int id) {
        log.debug("find user by id {}", id);
        return Optional.ofNullable(tx.txResult(session -> session.get(User.class, id)));
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        log.debug("find user by login and password {}", login);
        return Optional.ofNullable(tx.txResult(session
                ->
                session.createQuery("from User where login = :login and password = :password", User.class)
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .uniqueResult()));
    }

    @Override
    public boolean isLoginNameAvailable(String login) {
        log.debug("isLoginNameAvailable {}", login);
        return tx.txResult(session
                -> {
            User user = session.createQuery("from User where login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            return user == null;
        });
    }
}
