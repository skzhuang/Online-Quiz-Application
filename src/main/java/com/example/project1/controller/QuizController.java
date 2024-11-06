package com.example.project1.controller;

import com.example.project1.domain.*;
import com.example.project1.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final HttpSession session;

    @Autowired
    public QuizController(QuizService quizService, HttpSession session) {
        this.quizService = quizService;
        this.session = session;
    }

    @PostMapping("/start")
    public String startQuiz(@RequestParam("categoryId") int categoryId,
                            @SessionAttribute("user") User loggedInUser) {
        if (session.getAttribute("currentQuiz") == null) {
            // Create a new quiz
            String quizName = "User: " + loggedInUser.getEmail() + " Quiz on Category: " + categoryId;
            Quiz newQuiz = quizService.createQuiz(loggedInUser.getId(), categoryId, quizName);

            List<Question> questions = quizService.getRandomQuestionsByCategoryId(categoryId);
            List<QuizQuestion> quizQuestions = new ArrayList<>();
            for (Question question : questions) {
                quizQuestions.add(new QuizQuestion(newQuiz.getQuizId(), question.getId(), -1));
            }
            session.setAttribute("currentQuiz", newQuiz);
            session.setAttribute("questions", questions);
            session.setAttribute("quizQuestions", quizQuestions);
            session.setAttribute("currentIndex", 0);
            session.setAttribute("count", 0);
            session.setAttribute("totalQuestions", questions.size());
            System.out.println(questions.size());
        }
        return "redirect:/quiz/question";
    }

    @GetMapping("/question")
    public String getQuestion(Model model) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");

        if (currentIndex != null && currentIndex < questions.size()) {
            Question question = questions.get(currentIndex);
            List<Choice> choices = quizService.getChoicesByQuestionId(question.getId());
            List<QuizQuestion> quizQuestions = (List<QuizQuestion>) session.getAttribute("quizQuestions");
            QuizQuestion quizQuestion = quizQuestions.get(currentIndex);

            model.addAttribute("question", question);
            model.addAttribute("choices", choices);
            model.addAttribute("currentIndex", currentIndex);
            model.addAttribute("totalQuestions", session.getAttribute("totalQuestions"));
            if (quizQuestion.getUserChoiceId() != -1) {
                model.addAttribute("selectedChoiceId", quizQuestion.getUserChoiceId());
            }

            return "question";
        }
        return "redirect:/quiz/submit";
    }

    @PostMapping("/question/navigate")
    public String nextQuestion(@RequestParam(value = "selectedChoiceId", defaultValue = "-1") Integer selectedChoiceId,
                               @RequestParam("action") String move,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        if (selectedChoiceId != -1){
            List<QuizQuestion> quizQuestions = (List<QuizQuestion>) session.getAttribute("quizQuestions");
            QuizQuestion quizQuestion = quizQuestions.get(currentIndex);
            if (quizQuestion.getUserChoiceId() == -1){
                int count = (int) session.getAttribute("count") + 1;
                session.setAttribute("count", count);
                model.addAttribute("count", count);
            }
            quizQuestion.setUserChoiceId(selectedChoiceId);
            session.setAttribute("quizQuestions", quizQuestions);
        }

        if (move.contains("previous")) {
            if (currentIndex != null && currentIndex > 0) {
                currentIndex--;
                session.setAttribute("currentIndex", currentIndex);
            }
        } else if (move.contains("next")) {
            if (currentIndex != null && currentIndex < questions.size()) {
                currentIndex++;
                session.setAttribute("currentIndex", currentIndex);
            }
        } else {
            if (session.getAttribute("count") != session.getAttribute("totalQuestions")){
                redirectAttributes.addFlashAttribute("errorMessage", "Question not completed!");
                return "redirect:/quiz/question";
            }

            List<QuizQuestion> quizQuestions = (List<QuizQuestion>) session.getAttribute("quizQuestions");
            for (QuizQuestion quizQuestion : quizQuestions) {
                quizService.createQuizQuestion(quizQuestion);
            }
            Quiz currentQuiz = (Quiz) session.getAttribute("currentQuiz");
            quizService.updateQuiz(currentQuiz);
            int quizId = currentQuiz.getQuizId();
            session.removeAttribute("currentQuiz");
            return "redirect:/quiz/result?quizId=" + quizId;
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/result")
    public String submitQuiz(@RequestParam("quizId") int quizId,
                             Model model) {

        List<QuizQuestion> quizQuestions = quizService.getQuizQuestionsByQuizId(quizId);
        Quiz quiz = quizService.getQuizByQuizId(quizId);
        List<Result> results = new ArrayList<>();

        for (QuizQuestion quizQuestion : quizQuestions) {
            Result result = new Result();

            Integer questionId = quizQuestion.getQuestionId();
            Integer userChoiceId = quizQuestion.getUserChoiceId();

            Question question = quizService.getQuestionById(questionId);

            List<Choice> choices = quizService.getChoicesByQuestionId(questionId);
            Optional<Choice> realChoices = choices.stream()
                    .filter(Choice::isCorrect)
                    .findFirst();
            Optional<Choice> userChoice = choices.stream()
                    .filter(choice -> choice.getId()==userChoiceId)
                    .findFirst();

            result.setQuestion(question);
            if (realChoices.isPresent() && userChoice.isPresent()){
                result.setUserChoice(userChoice.get());
                result.setRealChoices(realChoices.get());
            }

            results.add(result);
        }

        model.addAttribute("quizName", quiz.getName());
        model.addAttribute("timeStart", quiz.getTimeStart());
        model.addAttribute("timeEnd", quiz.getTimeEnd());
        model.addAttribute("results", results);

        return "quizResult";
    }
}
