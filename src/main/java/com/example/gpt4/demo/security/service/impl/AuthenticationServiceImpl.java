package com.example.gpt4.demo.security.service.impl;

import com.example.gpt4.demo.security.dao.request.LoginRequest;
import com.example.gpt4.demo.security.dao.request.RegisterRequest;
import com.example.gpt4.demo.security.dao.response.JwtAuthenticationResponse;
import com.example.gpt4.demo.security.entities.Role;
import com.example.gpt4.demo.security.entities.User;
import com.example.gpt4.demo.security.repository.UserRepository;
import com.example.gpt4.demo.security.service.AuthenticationService;
import com.example.gpt4.demo.security.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    @Override
    public JwtAuthenticationResponse register(
        RegisterRequest request,
        HttpServletResponse response) {
        
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                        .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        addCookieToResponse(response, jwt);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
    
    @Override
    public JwtAuthenticationResponse login(
        LoginRequest request,
        HttpServletResponse response) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(user);
        addCookieToResponse(response, jwt);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
    
    @Override
    public Boolean authenticated(Authentication authentication) {
        
        return authentication.isAuthenticated();
    }
    
    private void addCookieToResponse(
        HttpServletResponse response,
        String jwt) {
        
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(6000000);
        response.addCookie(cookie);
    }
}
