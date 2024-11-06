package com.example.project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContactForm(
            @RequestParam("subject") String subject,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        System.out.println("subject: " + subject);
        System.out.println("email: " + email);
        System.out.println("message: " + message);

        return "contact";
    }
}
