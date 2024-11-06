package com.example.project1.controller;

import com.example.project1.domain.Category;
import com.example.project1.domain.Quiz;
import com.example.project1.domain.User;
import com.example.project1.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {this.homeService = homeService;}


    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user.isAdmin()) {
            return "redirect:/adminHome";
        }
        List<Category> quizCategories = homeService.getAllCategories();
        model.addAttribute("quizCategories", quizCategories);

        List<Quiz> quizs = homeService.getQuizsByUserId(user.getId());
        model.addAttribute("quizs", quizs);

        return "home";
    }

    @GetMapping("/adminHome")
    public String adminHome() {

        return "navbar";
    }
}

