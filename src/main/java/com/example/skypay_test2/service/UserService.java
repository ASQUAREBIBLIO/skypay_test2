package com.example.skypay_test2.service;

import com.example.skypay_test2.model.User;
import com.example.skypay_test2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

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
    }
}
