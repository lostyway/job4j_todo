package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.exception.CategoryNotFoundException;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryStore categoryStore;

    @Override
    public List<Category> getCategories() {
        var result = categoryStore.getCategories();
        if (result.isEmpty()) {
            throw new CategoryNotFoundException("Категорий не найдено");
        }
        return result;
    }

    @Override
    public List<Category> getCategoriesByIds(List<Integer> ids) {
        var result = categoryStore.getCategoriesByIds(ids);
        if (result.isEmpty()) {
            throw new CategoryNotFoundException("Категорий не найдено");
        }
        return result;
    }
}
