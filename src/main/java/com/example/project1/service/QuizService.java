package com.example.project1.service;

import com.example.project1.dao.ChoiceDao;
import com.example.project1.dao.QuestionDao;
import com.example.project1.dao.QuizDao;
import com.example.project1.dao.QuizQuestionDao;
import com.example.project1.domain.Choice;
import com.example.project1.domain.Question;
import com.example.project1.domain.Quiz;
import com.example.project1.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final ChoiceDao choiceDao;
    private final QuizQuestionDao quizQuestionDao;

    @Autowired
    public QuizService(QuizDao quizDao, QuestionDao questionDao, ChoiceDao choiceDao, QuizQuestionDao quizQuestionDao) {
        this.quizDao = quizDao;
        this.questionDao = questionDao;
        this.choiceDao = choiceDao;
        this.quizQuestionDao = quizQuestionDao;
    }

    public Quiz createQuiz(int userId, int categoryId, String quizName) {
        // Create new quiz with current timestamp
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(quizName);
        quiz.setTimeStart(new Timestamp(System.currentTimeMillis()));

        // Save to database
        int id = quizDao.createQuiz(quiz);
        quiz.setQuizId(id);
        return quiz;
    }

    public List<QuizQuestion> getQuizQuestionsByQuizId(int quizId) {
        return quizQuestionDao.getQuizQuestionsByQuizId(quizId);
    }

    public void updateQuiz(Quiz quiz) {
        quiz.setTimeEnd(new Timestamp(System.currentTimeMillis()));
        quizDao.updateQuiz(quiz);
    }

    public void createQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionDao.createQuizQuestion(quizQuestion.getQuizId(),quizQuestion.getQuestionId(),quizQuestion.getUserChoiceId());
    }

    public List<Question> getRandomQuestionsByCategoryId(int categoryId) {
        // Get 3 random questions from the category
        return questionDao.getRandomQuestionsByCategoryId(categoryId, 3);
    }

    public List<Choice> getChoicesByQuestionId(int questionId) {
        return choiceDao.getChoicesByQuestionId(questionId);
    }

    public Quiz getQuizByQuizId(int quizId) {
        return quizDao.getQuizByQuizId(quizId);
    }

    public Question getQuestionById(int questionId) {
        return questionDao.getQuestionById(questionId);
    }
}

