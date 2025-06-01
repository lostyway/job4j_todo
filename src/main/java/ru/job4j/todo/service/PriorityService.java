package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PriorityService implements IPriorityService {
    private final PriorityStore priorityStore;

    @Override
    public List<Priority> getPriorities() {
        var result = priorityStore.getPriorities();
        if (result.isEmpty()) {
            throw new RuntimeException("Такой приоритет не найден");
        }
        return result;
    }

    @Override
    public Priority findById(int priority) {
        return priorityStore.getPriorityById(priority)
                .orElseThrow(() -> new RuntimeException("Такой приоритет не найден"));
    }
}
