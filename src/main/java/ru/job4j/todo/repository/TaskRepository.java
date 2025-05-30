package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Repository
@AllArgsConstructor
public class TaskRepository implements ITaskRepository {
    private final SessionFactory sf;

    @Override
    public Optional<Task> addNewTask(Task task) {
        log.debug("addNewTask: {}", task);
        if (task == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(txResult(session -> {
            session.save(task);
            return task;
        }));
    }

    @Override
    public Optional<Task> updateTask(Task task) {
        log.debug("updateTask: {}", task);
        Optional<Task> taskToUpdate = getTaskById(task.getId());
        if (taskToUpdate.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(txResult(session -> (Task) session.merge(task)));
    }

    @Override
    public boolean deleteTask(Task task) {
        log.debug("deleteTask: {}", task);
        if (task == null) {
            return false;
        }
        return txResult(session -> {
            Task taskFromDbToDelete = session.get(Task.class, task.getId());
            if (taskFromDbToDelete == null) {
                return false;
            }
            session.delete(taskFromDbToDelete);
            return true;
        });
    }

    @Override
    public Optional<Task> getTaskById(int id) {
        return txResult(session -> Optional.ofNullable(session.get(Task.class, id)));
    }

    @Override
    public List<Task> getAllTasks() {
        return txResult(session
                -> session.createQuery("from Task order by created desc", Task.class).list());
    }

    @Override
    public List<Task> getTaskByCompleted(Boolean completed) {
        return txResult(session ->
                session.createQuery("from Task where completed = :completed order by created desc", Task.class)
                        .setParameter("completed", completed)
                        .list());
    }

    private void txRun(Consumer<Session> command) {
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

    private <T> T txResult(Function<Session, T> command) {
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
}
