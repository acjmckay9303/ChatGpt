package com.example.gpt4.demo.security.service;

import com.example.gpt4.demo.security.entities.Role;
import com.example.gpt4.demo.security.entities.User;
import com.example.gpt4.demo.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SystemMaintenanceService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @EventListener(ApplicationReadyEvent.class)
    private void createSystemUser() {
        
        userRepository.findByEmail("system@system.com")
                      .ifPresent(userRepository::delete);
        
        User user = User.builder()
                        .email("system@system.com")
                        .password(passwordEncoder.encode("system"))
                        .role(Role.ADMIN)
                        .createdDate(LocalDateTime.now())
                        .firstName("System")
                        .lastName("User")
                        .build();
        userRepository.save(user);
    }
}
