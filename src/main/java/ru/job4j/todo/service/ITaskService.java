package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;

public interface ITaskService {
    Task createTask(Task task);

    Task updateTask(Task task);

    boolean deleteTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int id);

    List<Task> getTaskByCompleted(Boolean completed);

    List<Task> getUserTaskByCompleted(User user, Boolean completed);

    List<Task> getUserAllTasks(User user);
}
