package com.example.project1.controller;

import com.example.project1.domain.User;
import com.example.project1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {this.userService = userService;}

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, HttpServletRequest request, Model model) {
        String confirmPassword = request.getParameter("confirmPassword");

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match!");
            return "register";
        }

        try {
            user.setActive(true);
            user.setAdmin(false);
            userService.createUser(user);
            model.addAttribute("successMessage", "Register success!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        return "redirect:/login";
    }
}
