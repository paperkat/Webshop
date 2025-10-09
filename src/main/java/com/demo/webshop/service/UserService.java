package com.demo.webshop.service;

import com.demo.webshop.entity.User;
import com.demo.webshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        // Check if username already exists
        List<User> usersWithSameUsername = userRepository.findByUsername(user.getUsername());
        if (!usersWithSameUsername.isEmpty()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        // Check if email already exists
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> { throw new IllegalArgumentException("Email already exists: " + user.getEmail()); });

        // Save the user if all checks pass
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
