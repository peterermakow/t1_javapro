package ru.paermakov.service;

import org.springframework.stereotype.Service;
import ru.paermakov.repository.UserRepository;
import ru.paermakov.entity.User;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userDAO) {
        this.userRepository = userDAO;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id).orElseThrow(() -> new RuntimeException("User not found in database"));
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }
}
