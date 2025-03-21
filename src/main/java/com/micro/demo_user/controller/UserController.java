package com.micro.demo_user.controller;

import com.micro.demo_user.model.User;
import com.micro.demo_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/public/welcome")
    public String welcome() {
        return "Welcome to the public API!";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "This is a user profile. Accessible only by USER role.";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Admin dashboard. Accessible only by ADMIN role.";
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);    }
}