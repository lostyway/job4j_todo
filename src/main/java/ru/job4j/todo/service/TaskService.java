package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskUpdateException;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.ITaskRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private final ITaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        valudateTask(task);
        return taskRepository.addNewTask(task)
                .orElseThrow(() -> new TaskUpdateException("Не удалось сохранить задачу"));
    }

    @Override
    public Task updateTask(Task task) {
        valudateTask(task);
        return taskRepository.updateTask(task)
                .orElseThrow(() -> new TaskUpdateException("Не удалось обновить задачу"));
    }

    @Override
    public boolean deleteTask(Task task) {
        valudateTask(task);
        return taskRepository.deleteTask(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    @Override
    public List<Task> getTaskByCompleted(Boolean completed) {
        return taskRepository.getTaskByCompleted(completed);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с id: " + id + " не была найдена!"));
    }

    private void valudateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Не удалось создать задачу. Она пуста");
        }
    }
}
