package com.example.gpt4.demo.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    UserDetailsService userDetailsService();
}
