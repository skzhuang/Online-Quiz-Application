package com.example.project1.dao;

import com.example.project1.domain.QuizQuestion;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public List<QuizQuestion> getQuizQuestionsByQuizId(int quizId) {
        String sql = "select * from quizQuestion where quiz_id = ?";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuizQuestion.class), quizId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }    }

    public void createQuizQuestion(int quizId, int questionId, int selectedChoiceId) {
        String sql = "INSERT INTO quizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, quizId, questionId, selectedChoiceId);
    }
}
