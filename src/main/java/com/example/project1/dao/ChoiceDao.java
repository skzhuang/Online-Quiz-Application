package com.example.project1.dao;

import com.example.project1.domain.Choice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChoiceDao {
    private final JdbcTemplate jdbcTemplate;

    public ChoiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Choice> getChoicesByQuestionId(int questionId) {
        String sql = "select * from choice where question_id=?";
        return jdbcTemplate.query(sql, new ChoiceRowMapper(), questionId);
    }

    public static class ChoiceRowMapper implements RowMapper<Choice> {
        @Override
        public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
            Choice choice = new Choice();
            choice.setId(rs.getInt("choice_id"));
            choice.setQuestionId(rs.getInt("question_id"));
            choice.setDescription(rs.getString("description"));
            choice.setCorrect(rs.getBoolean("is_correct"));
            return choice;
        }
    }
}
