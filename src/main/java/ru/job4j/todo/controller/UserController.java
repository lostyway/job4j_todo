package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.exception.UserNotFoundException;
import ru.job4j.todo.exception.UserWithSameLoginAlreadyExist;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login";
    }

    @GetMapping("register")
    public String getRegisterPage() {
        return "user/register";
    }


    @PostMapping("/login")
    public String loginByEmailAndPassword(@ModelAttribute User user, Model model, HttpServletRequest request) {
        try {
            User userToLogin = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
            request.getSession().setAttribute("user", userToLogin);
            request.getSession().setAttribute("userId", userToLogin.getId());
            return "redirect:/tasks";
        } catch (IllegalArgumentException | UserNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "/user/login";
        } catch (Exception e) {
            log.error("Необработанное исключение в методе loginByEmailAndPassword", e);
            model.addAttribute("error", "Произошла непредвиденная ошибка");
            return "errors/404";
        }
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute User user, Model model) {
        try {
            userService.save(user);
            return "redirect:/user/login";
        } catch (IllegalArgumentException | UserWithSameLoginAlreadyExist e) {
            model.addAttribute("error", e.getMessage());
            return "errors/404";
        } catch (Exception e) {
            log.error("Необработанное исключение в методе register", e);
            model.addAttribute("error", "Произошла непредвиденная ошибка. Попробуйте позже");
            return "errors/404";
        }
    }

    @GetMapping("/logout")
    public String makeLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
