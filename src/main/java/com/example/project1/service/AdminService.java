package com.example.project1.service;

import com.example.project1.dao.QuestionDao;
import com.example.project1.dao.QuizDao;
import com.example.project1.domain.Question;
import com.example.project1.domain.QuizResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final QuestionDao questionDao;
    private final QuizDao quizDao;

    public AdminService(QuestionDao questionDao, QuizDao quizDao) {
        this.questionDao = questionDao;
        this.quizDao = quizDao;
    }

    public void toggleQuestionStatus(int questionId) {
        questionDao.toggleQuestionStatus(questionId);
    }

    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public void deleteQuestionByQuestionId(int questionId) {
        questionDao.deleteQuestionByQuestionId(questionId);
    }

    public Question getQuestionById(int questionId) {
        return questionDao.getQuestionById(questionId);
    }

    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }

    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }

    public List<QuizResult> getQuizResults(int page, String category, String userFullName, String sort) {
        int pageSize = 5;
        return quizDao.getQuizResults(category, userFullName, sort, page, pageSize);
    }

    public int getTotalPages(String category, String userFullName) {
        return (int) Math.ceil(quizDao.getTotalPages(category, userFullName) / 5.0);
    }
}
