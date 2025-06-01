package ru.job4j.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskUpdateException;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    private static TaskService taskService;
    private TaskStore repository;
    private Task task;
    private Priority priority;
    private User user;

    @BeforeEach
    void setUp() {
        repository = mock(TaskStore.class);
        taskService = new TaskService(repository);
        task = new Task("Test task1", true, priority, user, List.of());
        priority = new Priority("urgurg", 400);
        user = new User("testing", "testing", "testing");
    }

    @Test
    void whenCreateNewTaskIsSuccessful() {
        when(repository.addNewTask(any())).thenReturn(Optional.of(new Task("Test task1", true, priority, user, List.of())));
        Task task = taskService.createTask(new Task("Test task1", true, priority, user, List.of()));
        assertThat(task).isNotNull();
    }

    @Test
    void whenCreateNewTaskIsFailedBecauseOfNull() {
        assertThatThrownBy(() -> taskService.createTask(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Не удалось создать задачу. Она пуста");
    }

    @Test
    void whenCreateNewTaskIsFailedBecauseNewTaskIsEmpty() {
        when(repository.addNewTask(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.createTask(new Task("Test task1", true, priority, user, List.of())))
                .isInstanceOf(TaskUpdateException.class)
                .hasMessageContaining("Не удалось сохранить задачу");
    }

    @Test
    void whenUpdateTaskIsSuccess() {
        when(repository.updateTask(any())).thenReturn(Optional.of(new Task("Test task1", true, priority, user, List.of())));
        Task task = taskService.updateTask(new Task("Test task1", true, priority, user, List.of()));
        assertThat(task).isNotNull();
    }

    @Test
    void whenUpdateTaskIsFailedBecauseOfEmptyTask() {
        when(repository.updateTask(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.updateTask(new Task("Test task1", true, priority, user, List.of())))
                .isInstanceOf(TaskUpdateException.class)
                .hasMessageContaining("Не удалось обновить задачу");
    }

    @Test
    void whenDeleteTaskIsSuccess() {
        when(repository.deleteTask(any())).thenReturn(true);
        boolean result = taskService.deleteTask(new Task("Test task1", true, priority, user, List.of()));
        assertThat(result).isTrue();
    }

    @Test
    void whenDeleteTaskIsFailed() {
        when(repository.deleteTask(any())).thenReturn(false);
        assertThatThrownBy(() -> taskService.deleteTask(new Task("Test task1", true, priority, user, List.of())))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Не удалось удалить задачу");
    }

    @Test
    void whenGetAllTaskIsSuccess() {
        when(repository.getAllTasks()).thenReturn(List.of(new Task(), new Task()));
        List<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).isNotEmpty();
        assertThat(tasks).hasSize(2);
    }

    @Test
    void whenGetAllTaskIsSuccessWithEmptyList() {
        when(repository.getAllTasks()).thenReturn(List.of());
        List<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).isEmpty();
    }

    @Test
    void whenGetAllTaskByCompletedIsSuccess() {
        when(repository.getAllTaskByCompletable(true))
                .thenReturn(List.of(
                        new Task("Test task1", true, priority, user, List.of()),
                        new Task("Test task1", true, priority, user, List.of())));
        List<Task> tasks = taskService.getTaskByCompleted(true);
        assertThat(tasks).hasSize(2);
    }

    @Test
    void whenGetAllTaskByCompletedIsSuccessWithEmptyList() {
        when(repository.getAllTaskByCompletable(true))
                .thenReturn(List.of());
        List<Task> tasks = taskService.getTaskByCompleted(true);
        assertThat(tasks).isEmpty();
    }
}