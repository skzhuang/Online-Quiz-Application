package com.example.project1.dao;

import com.example.project1.domain.Quiz;
import com.example.project1.domain.QuizResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create a new quiz
    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, quiz.getUserId());
            ps.setInt(2, quiz.getCategoryId());
            ps.setString(3, quiz.getName());
            ps.setTimestamp(4, quiz.getTimeStart());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else return -1;
    }

    public void updateQuiz(Quiz quiz) {
        String sql = "UPDATE Quiz SET time_end = ? WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quiz.getTimeEnd(), quiz.getQuizId());
    }

    public Quiz getQuizByQuizId(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new QuizRowMapper(), quizId);
    }

    public List<Quiz> getUserById(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ?";
        return jdbcTemplate.query(sql, new QuizRowMapper(), userId);
    }

    public List<QuizResult> getQuizResults(String category, String userFullName, String sortColumn, int page, int size) {
        StringBuilder sql = new StringBuilder("SELECT q.quiz_id, q.time_end AS taken_time, " +
                "CONCAT(u.firstname, ' ', u.lastname) AS userFullName, " +
                "c.name AS categoryName, COUNT(que.question_id) AS number_of_questions " +
                "FROM Quiz q " +
                "JOIN User u ON q.user_id = u.user_id " +
                "JOIN Category c ON q.category_id = c.category_id " +
                "JOIN QuizQuestion qq ON q.quiz_id = qq.quiz_id " +
                "JOIN Question que ON qq.question_id = que.question_id " +
                "WHERE 1 = 1");

        List<Object> params = new ArrayList<>();

        // Apply filters as needed
        if (category != null && !category.isEmpty()) {
            sql.append(" AND c.name = ?");
            params.add(category);
        }

        if (userFullName != null && !userFullName.isEmpty()) {
            sql.append(" AND (CONCAT(u.firstname, ' ', u.lastname) LIKE ?)");
            params.add("%" + userFullName + "%");
        }

        sql.append(" GROUP BY q.quiz_id, q.time_end, u.firstname, u.lastname, c.name");

        // Sorting logic
        if (sortColumn != null && !sortColumn.isEmpty()) {
            if (sortColumn.equals("taken_time")) {
                sql.append(" ORDER BY ").append(sortColumn).append(" DESC");
            } else {
                sql.append(" ORDER BY ").append(sortColumn);
            }
        } else {
            sql.append(" ORDER BY q.time_end DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add((page - 1) * size);

        return jdbcTemplate.query(sql.toString(), params.toArray(), new QuizResultRowMapper());
    }

    public Integer getTotalPages(String category, String userFullName) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) " +
                "FROM (" +
                "SELECT COUNT(*) " +
                "FROM Quiz q " +
                "JOIN User u ON q.user_id = u.user_id " +
                "JOIN Category c ON q.category_id = c.category_id " +
                "WHERE 1 = 1");

        List<Object> params = new ArrayList<>();

        if (category != null && !category.isEmpty()) {
            sql.append(" AND c.name = ?");
            params.add(category);
        }

        if (userFullName != null && !userFullName.isEmpty()) {
            sql.append(" AND (CONCAT(u.firstname, ' ', u.lastname) LIKE ?)");
            params.add("%" + userFullName + "%");
        }

        sql.append(" GROUP BY q.quiz_id) AS target");

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public static class QuizRowMapper implements RowMapper<Quiz> {
        @Override
        public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {
            Quiz quiz = new Quiz();
            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setCategoryId(rs.getInt("category_id"));
            quiz.setName(rs.getString("name"));
            quiz.setTimeStart(rs.getTimestamp("time_start"));
            quiz.setTimeEnd(rs.getTimestamp("time_end"));
            return quiz;
        }
    }

    public static class QuizResultRowMapper implements RowMapper<QuizResult> {
        @Override
        public QuizResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuizResult quizResult = new QuizResult();
            quizResult.setQuizId(rs.getInt("quiz_id"));
            quizResult.setCategoryName(rs.getString("categoryName"));
            quizResult.setUserFullName(rs.getString("userFullName"));
            quizResult.setTakenTime(rs.getTimestamp("taken_time"));
            quizResult.setQuestionsNumber(rs.getInt("number_of_questions"));
            return quizResult;
        }
    }
}
