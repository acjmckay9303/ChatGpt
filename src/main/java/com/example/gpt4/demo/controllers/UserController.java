package com.example.gpt4.demo.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    
    @GetMapping("/user/me")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    
    @GetMapping("/user/jwt")
    public ResponseEntity<String> getJWT(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
        OAuth2User oauth2User = authentication.getPrincipal();
        
        // The "subject" of the JWT is typically the user's username or user ID
        String subject = oauth2User.getName();
        
        // Additional claims can be added as needed
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", oauth2User.getAttribute("name"));
        claims.put("email", oauth2User.getAttribute("email"));
        
        String jwt = Jwts.builder()
                         .setSubject(subject)
                         .setClaims(claims)
                         .setIssuedAt(new Date())
                         .setExpiration(new Date((new Date()).getTime() + 3600000))  // 1 hour expiration
                         .signWith(SignatureAlgorithm.HS256, "test")  // replace "your-secret-key" with your own secret key
                         .compact();
        
        return ResponseEntity.ok(jwt);
    }
}
