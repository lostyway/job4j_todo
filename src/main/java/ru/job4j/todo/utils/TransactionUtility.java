package ru.job4j.todo.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionUtility {
    private final SessionFactory sf;

    public <T> T txResult(Function<Session, T> command) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            log.error("Ошибка при работе с транзакцией", e);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void txVoid(Consumer<Session> command) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            command.accept(session);
            tx.commit();
        } catch (Exception e) {
            log.error("Ошибка при работе с транзакцией", e);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
