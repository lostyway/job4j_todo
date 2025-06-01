package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.utils.TransactionUtility;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryStore implements ICategoryStore {
    private final TransactionUtility tx;

    @Override
    public List<Category> getCategories() {
        return tx.txResult(session
                -> session.createQuery("select distinct c from Category c", Category.class)
                .list());
    }

    @Override
    public List<Category> getCategoriesByIds(List<Integer> ids) {
        return tx.txResult(session
                -> session.createQuery("select distinct c from Category c where c.id in (:ids)", Category.class)
                .setParameter("ids", ids)
                .list());
    }
}
