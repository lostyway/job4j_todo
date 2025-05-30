package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class TaskStore implements ITaskStore {
    private final TransactionUtility tx;

    @Override
    public Optional<Task> addNewTask(Task task) {
        log.debug("addNewTask: {}", task);
        if (task == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(tx.txResult(session -> {
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
        return Optional.ofNullable(tx.txResult(session -> (Task) session.merge(task)));
    }

    @Override
    public boolean deleteTask(Task task) {
        log.debug("deleteTask: {}", task);
        if (task == null) {
            return false;
        }
        return tx.txResult(session -> {
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
        return tx.txResult(session -> Optional.ofNullable(session.get(Task.class, id)));
    }

    @Override
    public List<Task> getAllTasks() {
        return tx.txResult(session
                -> session.createQuery("from Task order by created desc", Task.class).list());
    }

    @Override
    public List<Task> getAllTaskByCompletable(Boolean completed) {
        return tx.txResult(session ->
                session.createQuery("from Task where completed = :completed order by created desc", Task.class)
                        .setParameter("completed", completed)
                        .list());
    }
}
