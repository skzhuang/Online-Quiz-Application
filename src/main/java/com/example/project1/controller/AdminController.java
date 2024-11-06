package com.example.project1.controller;

import com.example.project1.domain.Question;
import com.example.project1.domain.QuizResult;
import com.example.project1.domain.User;
import com.example.project1.service.AdminService;
import com.example.project1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/userManagement")
    public String userManagement(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userManagement";
    }

    @PostMapping("/toggleStatus")
    public String toggleUserStatus(@RequestParam("userId") int userId) {
        userService.toggleUserStatus(userId);
        return "redirect:/admin/userManagement";
    }

    @GetMapping("/questionManagement")
    public String questionManagement(Model model) {
        List<Question> questions = adminService.getAllQuestions();
        model.addAttribute("questions", questions);
        return "questionManagement";
    }

    @GetMapping("/editQuestion")
    public String editQuestion(@RequestParam("questionId") int questionId, Model model) {
        Question question = adminService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("action", "edit");
        return "questionForm";
    }

    @GetMapping("/addQuestion")
    public String addQuestion(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("action", "add");
        return "questionForm";
    }

    @PostMapping("/editQuestion")
    public String updateQuestion(@ModelAttribute("question") Question question,
                                 @RequestParam("isActive") boolean isActive) {
        question.setActive(isActive);
        adminService.updateQuestion(question);
        return "redirect:/admin/questionManagement";
    }

    @PostMapping("/addQuestion")
    public String saveQuestion(@ModelAttribute("question") Question question,
                               @RequestParam("isActive") boolean isActive) {
        question.setActive(isActive);
        adminService.addQuestion(question);
        return "redirect:/admin/questionManagement";
    }

    @PostMapping("/toggleQuestionStatus")
    public String toggleQuestionStatus(@RequestParam("questionId") int questionId) {
        adminService.toggleQuestionStatus(questionId);
        return "redirect:/admin/questionManagement";
    }

    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") int questionId) {
        adminService.deleteQuestionByQuestionId(questionId);
        return "redirect:/admin/questionManagement";
    }

    @GetMapping("/quizResultManagement")
    public String getQuizResults(@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "category", required = false) String category,
                                 @RequestParam(name = "user", required = false) String userFullName,
                                 @RequestParam(name = "sort", required = false) String sort,
                                 HttpSession session,
                                 Model model) {

        session.setAttribute("sort", sort);
        String curSort = (session.getAttribute("sort") != null)
                ? session.getAttribute("sort").toString()
                : null;

        session.setAttribute("category", category);
        String curCategory = (session.getAttribute("category") != null)
                ? session.getAttribute("category").toString()
                : null;

        List<QuizResult> quizResults = adminService.getQuizResults(page, curCategory, userFullName, curSort);
        int totalPages = adminService.getTotalPages(curCategory, userFullName);
        model.addAttribute("quizResults", quizResults);
        model.addAttribute("totalPages", totalPages);

        return "quizResultManagement";
    }
}
