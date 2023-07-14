package com.example.gpt4.demo.security.controller;

import com.example.gpt4.demo.security.dao.request.LoginRequest;
import com.example.gpt4.demo.security.dao.request.RegisterRequest;
import com.example.gpt4.demo.security.dao.response.JwtAuthenticationResponse;
import com.example.gpt4.demo.security.entities.User;
import com.example.gpt4.demo.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(
        @RequestBody RegisterRequest request,
        HttpServletResponse response) {
        
        return ResponseEntity.ok(authenticationService.register(request, response));
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(
        @RequestBody LoginRequest request,
        HttpServletResponse response) {
        
        return ResponseEntity.ok(authenticationService.login(request, response));
    }
    
    @GetMapping("/authenticated")
    public ResponseEntity<Boolean> authenticated(
        Authentication authentication) {
    
        if (authentication == null) {return ResponseEntity.ok(false);}
        
        return ResponseEntity.ok(authenticationService.authenticated(authentication));
    }
    
    @GetMapping("/user")
    public ResponseEntity<User> getUser(Authentication authentication) {
    
        if (authentication == null) {return ResponseEntity.ok().build();}
        
        return ResponseEntity.ok((User) authentication.getPrincipal());
    }
}
