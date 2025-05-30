package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskUpdateException;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String tasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("task", new Task());
        return "create";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            Task savedTask = taskService.createTask(task);
            return "redirect:/tasks";
        } catch (TaskUpdateException e) {
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
    public String updateTask(@ModelAttribute Task task, Model model) {
        try {
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
}
