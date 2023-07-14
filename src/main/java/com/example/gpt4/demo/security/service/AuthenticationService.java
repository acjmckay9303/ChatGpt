package com.example.gpt4.demo.security.service;

import com.example.gpt4.demo.security.dao.request.LoginRequest;
import com.example.gpt4.demo.security.dao.request.RegisterRequest;
import com.example.gpt4.demo.security.dao.response.JwtAuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    
    JwtAuthenticationResponse register(
        RegisterRequest request,
        HttpServletResponse response);
    
    JwtAuthenticationResponse login(
        LoginRequest request,
        HttpServletResponse response);
    
    Boolean authenticated(Authentication authentication);
}
