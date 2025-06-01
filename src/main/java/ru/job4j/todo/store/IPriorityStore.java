package ru.job4j.todo.store;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface IPriorityStore {
    List<Priority> getPriorities();

    Optional<Priority> getPriorityById(int priority);
}
