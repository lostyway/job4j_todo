package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface ICategoryStore {

    List<Category> getCategories();

    List<Category> getCategoriesByIds(List<Integer> ids);

}
