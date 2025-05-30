package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskStore {
    Optional<Task> addNewTask(Task task);
    Optional<Task> updateTask(Task task);
    boolean deleteTask(Task task);
    Optional<Task> getTaskById(int id);
    List<Task> getAllTasks();
    List<Task> getAllTaskByCompletable(Boolean completed);
}
