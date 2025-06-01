package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getCategories();

    List<Category> getCategoriesByIds(List<Integer> ids);
}
