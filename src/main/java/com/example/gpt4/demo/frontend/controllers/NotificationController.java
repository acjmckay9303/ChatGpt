package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.entity.Notification;
import com.example.gpt4.demo.frontend.repositories.repositories.NotificationRepository;
import com.example.gpt4.demo.security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("secure/notifications")
public class NotificationController {
    
    private final NotificationRepository notificationRepository;
    
    @GetMapping
    public Iterable<Notification> getAllForUserNotifications(Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        return notificationRepository.findAllByUserId(user.getId());
    }
}
