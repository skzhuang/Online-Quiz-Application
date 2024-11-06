package com.example.project1.dao;

import com.example.project1.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> getAllCategories() {
        String sql = "SELECT category_id, name FROM category";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    private static class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("name"));
            return category;
        }
    }
}
