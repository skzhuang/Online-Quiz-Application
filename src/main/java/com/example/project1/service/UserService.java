package com.example.project1.service;

import com.example.project1.dao.UserDao;
import com.example.project1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public Optional<User> validateLogin(String email, String password) {
        return userDao.validateLogin(email, password);
    }


    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void toggleUserStatus(int userId) {
        userDao.toggleUserStatus(userId);
    }
}
