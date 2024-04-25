package ru.paermakov.service;

import ru.paermakov.dao.UserDAO;
import ru.paermakov.entity.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(Long id) {
        return userDAO.getUserById(id).orElseThrow(() -> new RuntimeException("User not found in database"));
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public void deleteUserById(Long id) {
        userDAO.deleteUserById(id);
    }
}
