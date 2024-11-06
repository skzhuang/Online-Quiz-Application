package com.example.project1.dao;

import com.example.project1.domain.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        String sql = "INSERT INTO User (email, firstname, lastname, password, is_active, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, user.getEmail(), user.getFirstname(), user.getLastname(), user.getPassword(), user.isActive(), user.isAdmin());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email exists!");
        }
    }

    public Optional<User> validateLogin(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{email, password}, new UserRowMapper()));
        } catch (Exception e) {
            System.out.println("Can't find user: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public void toggleUserStatus(int userId) {
        String sql = "UPDATE user SET is_active = NOT is_active WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setEmail(rs.getString("email"));
            user.setFirstname(rs.getString("firstname"));
            user.setLastname(rs.getString("lastname"));
            user.setActive(rs.getBoolean("is_active"));
            user.setAdmin(rs.getBoolean("is_admin"));
            return user;
        }
    }
}
