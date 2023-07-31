package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.entity.Notification;
import com.example.gpt4.demo.frontend.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("secure/notifications")
public class NotificationController {
    
    private final NotificationRepository notificationRepository;
    
    @GetMapping
    public Flux<Notification> getAllForUserNotifications(Authentication authentication) {
        
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return notificationRepository.findAllByUserId(user.getUsername());
    }
}
