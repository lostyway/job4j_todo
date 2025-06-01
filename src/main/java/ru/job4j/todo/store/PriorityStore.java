package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Priority> getPriorityById(int priority) {
        return Optional.ofNullable(tx.txResult(session
                        -> session.createQuery("from Priority where id = :id", Priority.class)
                .setParameter("id", priority).getSingleResult()));
    }
}
