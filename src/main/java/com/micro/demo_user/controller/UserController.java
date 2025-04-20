package com.micro.demo_user.controller;

import com.micro.demo_user.dto.PlaceOrderRequest;
import com.micro.demo_user.model.User;
import com.micro.demo_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/public")
    public String welcome() {
        return "Welcome to the public API!";
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequest request) {
        String orchestratorUrl = "http://localhost:8086/orchestrator/place";
        ResponseEntity<String> response = restTemplate.postForEntity(orchestratorUrl, request, String.class);
        return ResponseEntity.ok("Order placed! Response from order service: " + response.getBody());
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
}