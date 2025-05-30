package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskUpdateException;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.ITaskStore;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private final ITaskStore taskRepository;

    @Override
    public Task createTask(Task task) {
        validateTask(task);
        return taskRepository.addNewTask(task)
                .orElseThrow(() -> new TaskUpdateException("Не удалось сохранить задачу"));
    }

    @Override
    public Task updateTask(Task task) {
        validateTask(task);
        return taskRepository.updateTask(task)
                .orElseThrow(() -> new TaskUpdateException("Не удалось обновить задачу"));
    }

    @Override
    public boolean deleteTask(Task task) {
        validateTask(task);
        boolean isDeleted = taskRepository.deleteTask(task);
        if (!isDeleted) {
            throw new TaskNotFoundException("Не удалось удалить задачу");
        }
        return true;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    @Override
    public List<Task> getTaskByCompleted(Boolean completed) {
        return taskRepository.getAllTaskByCompletable(completed);
    }

    @Override
    public List<Task> getUserTaskByCompleted(User user, Boolean completed) {
        return taskRepository.getAllUserTaskByCompletable(user, completed);
    }

    @Override
    public List<Task> getUserAllTasks(User user) {
        return taskRepository.getAllUserTask(user);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с id: " + id + " не была найдена!"));
    }

    private void validateTask(Task task) {
        if (task == null || task.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Не удалось создать задачу. Она пуста");
        }
    }
}
