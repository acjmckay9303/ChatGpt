package com.example.gpt4.demo.security.controllers;

import com.example.gpt4.demo.security.config.UserAuthenticationProvider;
import com.example.gpt4.demo.security.dto.CredentialsDto;
import com.example.gpt4.demo.security.dto.SignUpDto;
import com.example.gpt4.demo.security.dto.UserDto;
import com.example.gpt4.demo.security.services.AuthenticationService;
import com.example.gpt4.demo.security.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final AuthenticationService authenticationService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid CredentialsDto credentials) { // Modify this line
        UserDto authenticatedUser = authenticationService.authenticate(credentials); // Add this line
        String token = userAuthenticationProvider.createToken(authenticatedUser.getLogin());
        log.info("User [{}] logged in with token [{}]", credentials.getLogin());
        return ResponseEntity.ok(token); // Modify
        // this line
    }
    
    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        
        return null;
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDto user) {
        
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateUserToken(@AuthenticationPrincipal UserDto user) {
        
        return ResponseEntity.ok(userAuthenticationProvider.checkToken(user.getLogin()));
    }
}