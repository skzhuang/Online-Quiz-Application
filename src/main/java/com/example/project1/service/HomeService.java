package com.example.project1.service;

import com.example.project1.dao.CategoryDao;
import com.example.project1.dao.QuizDao;
import com.example.project1.domain.Category;
import com.example.project1.domain.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    private final CategoryDao categoryDao;
    private final QuizDao quizDao;

    public HomeService(CategoryDao categoryDao, QuizDao quizDao) {
        this.categoryDao = categoryDao;
        this.quizDao = quizDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public List<Quiz> getQuizsByUserId(int userId) {
        return quizDao.getUserById(userId);
    }
}
