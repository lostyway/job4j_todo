package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.exception.CategoryNotFoundException;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskUpdateException;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String tasks(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.getUserAllTasks(user);
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        try {
            model.addAttribute("task", new Task());
            model.addAttribute("priorities", priorityService.getPriorities());
            model.addAttribute("categories", categoryService.getCategories());
            return "create";
        } catch (CategoryNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Task task, @RequestParam("priority.id") int priority,
                         @RequestParam(required = false, name = "categoryIds") List<Integer> ids,
                         @SessionAttribute User user, Model model) {

        if (ids == null || ids.isEmpty()) {
            model.addAttribute("error", "Нужно выбрать хотя бы одну категорию");
            return "/errors/404";
        }

        try {
            task.setUser(user);
            task.setPriority(priorityService.findById(priority));
            task.setCategories(categoryService.getCategoriesByIds(ids));
            taskService.createTask(task);
            return "redirect:/tasks";
        } catch (TaskUpdateException | CategoryNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @GetMapping("/view/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "view";
        } catch (TaskNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable int id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            model.addAttribute("priorities", priorityService.getPriorities());
            model.addAttribute("categories", categoryService.getCategories());
            return "edit";
        } catch (TaskNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task,@RequestParam(required = false) List<Integer> categoryIds ,Model model) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            model.addAttribute("error", "Нужно выбрать хотя бы одну категорию");
            return "/errors/404";
        }

        try {
            List<Category> categories = categoryService.getCategoriesByIds(categoryIds);
            task.setCategories(new ArrayList<>(categories));
            taskService.updateTask(task);
            return "redirect:/tasks";
        } catch (TaskUpdateException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            taskService.deleteTask(task);
            return "redirect:/tasks";
        } catch (TaskUpdateException | TaskNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @PostMapping("/done/{id}")
    public String completeTask(@PathVariable int id, Model model) {
        try {
            Task task = taskService.getTaskById(id);
            task.setCompleted(true);
            taskService.updateTask(task);
            return "redirect:/tasks";
        } catch (TaskUpdateException | TaskNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/errors/404";
        } catch (Exception e) {
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "/errors/404";
        }
    }

    @GetMapping("/completed")
    public String listTasksOfCompleted(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.getUserTaskByCompleted(user, true);
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/new")
    public String listNewTasks(Model model, @SessionAttribute User user) {
        List<Task> tasks = taskService.getUserTaskByCompleted(user, false);
        model.addAttribute("tasks", tasks);
        return "list";
    }
}
