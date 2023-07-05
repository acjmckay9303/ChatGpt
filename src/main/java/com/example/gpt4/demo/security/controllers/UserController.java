package com.example.gpt4.demo.security.controllers;

import com.example.gpt4.demo.security.entity.User;
import com.example.gpt4.demo.security.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("insecure/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/create")
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }
}
