package com.micro.demo_user.service;

import com.micro.demo_user.model.User;
import com.micro.demo_user.model.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.StaleObjectStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired private UserEventPublisher userEventPublisher;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Retryable(value = StaleObjectStateException.class, maxAttempts = 3)
    @Transactional
    public void updateUser(Long userId, String newName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(newName);
        userRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("New user cannot have an ID");
        }
        userEventPublisher.publishUserCreatedEvent("Hello from ( " + user.getName() + " ) user in user-service");
        return userRepository.save(user);
    }
    @Cacheable(value = "users", key = "#userId")
    public User getUserById(Long userId) {
        System.out.println("Fetching from DB for userId: " + userId);
        return userRepository.findById(userId).orElse(null);
    }
}
