package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.List;

public interface IPriorityService {
    List<Priority> getPriorities();

    Priority findById(int priority);
}
