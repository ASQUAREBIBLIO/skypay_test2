package com.example.skypay_test2.service;

import com.example.skypay_test2.model.User;
import com.example.skypay_test2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    /**
     * For API
     * private final UserRepository userRepo;

    @Transactional
    public User setUser(String name, double balance) {
        User user = new User();
        user.setName(name);
        user.setBalance(balance);
        return  userRepo.save(user);
    }

    @Transactional()
    public User getUserById(Long id) {
        return userRepo
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User updateBalance(Long userId, Double newBalance) {
        User user = getUserById(userId);
        user.setBalance(newBalance);
        return userRepo.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }*/

    private final List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    public User setUser(String name, Double balance) {
        User user = new User();
        user.setId(nextId++);
        user.setName(name);
        user.setBalance(balance);
        users.add(user);
        return user;
    }

    public User getUserById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateBalance(Long userId, Double newBalance) {
        User user = getUserById(userId);
        user.setBalance(newBalance);
        return user;
    }

    public List<User> printAllUsers() {
        return new ArrayList<>(users);
    }
}
