package com.example.project1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Value("${spring.datasource.driver-class-name}")
    private final String JDBC_DRIVER = null;
    @Value("${spring.datasource.url}")
    private final String DB_URL = null;
    @Value("${spring.datasource.username}")
    private final String USER = null;
    @Value("${spring.datasource.password}")
    private final String PASSWORD = null;

    @Bean
    public DataSource jdbcDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
