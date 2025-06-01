package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class PriorityStore implements IPriorityStore {
    private final TransactionUtility tx;

    @Override
    public List<Priority> getPriorities() {
        return tx.txResult(session
                -> session.createQuery("from Priority", Priority.class).list());
    }
}
