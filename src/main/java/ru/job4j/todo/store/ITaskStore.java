package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface ITaskStore {
    Optional<Task> addNewTask(Task task);
    Optional<Task> updateTask(Task task);
    boolean deleteTask(Task task);
    Optional<Task> getTaskById(int id);
    List<Task> getAllTasks();
    List<Task> getAllTaskByCompletable(Boolean completed);
    List<Task> getAllUserTask(User user);
    List<Task> getAllUserTaskByCompletable(User user, Boolean completed);
}
