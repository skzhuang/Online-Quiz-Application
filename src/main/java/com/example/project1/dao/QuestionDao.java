package com.example.project1.dao;

import com.example.project1.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get three random questions from the category
    public List<Question> getRandomQuestionsByCategoryId(int categoryId, int limit) {
        String sql = "SELECT * FROM Question WHERE category_id = ? AND is_active = TRUE ORDER BY RAND() LIMIT ?";
        return jdbcTemplate.query(sql, new QuestionRowMapper(), categoryId, limit);
    }

    public Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        return jdbcTemplate.queryForObject(sql, new QuestionRowMapper(), questionId);
    }

    public void toggleQuestionStatus(int questionId) {
        String sql = "UPDATE Question SET is_active = NOT is_active WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }

    public List<Question> getAllQuestions() {
        String sql = "SELECT * FROM Question";
        return jdbcTemplate.query(sql, new QuestionRowMapper());
    }

    public void deleteQuestionByQuestionId(int questionId) {
        String sql = "DELETE FROM Question WHERE question_id = ?";
        jdbcTemplate.update(sql, questionId);
    }

    public void updateQuestion(Question question) {
        String sql = "UPDATE Question SET category_id = ?, description = ?, is_active = ? WHERE question_id = ?";

        jdbcTemplate.update(sql,
                question.getCategoryId(),
                question.getDescription(),
                question.isActive(),
                question.getId()
        );
    }

    public void addQuestion(Question question) {
        String sql = "INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                question.getCategoryId(),
                question.getDescription(),
                question.isActive()
        );
    }

    public static class QuestionRowMapper implements RowMapper<Question> {
        @Override
        public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
            Question question = new Question();
            question.setId(rs.getInt("question_id"));
            question.setCategoryId(rs.getInt("category_id"));
            question.setDescription(rs.getString("description"));
            question.setActive(rs.getBoolean("is_active"));
            return question;
        }
    }
}
