package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.Task;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String tasks() {
        return "list";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute Task task) {
        return "create";
    }
}
