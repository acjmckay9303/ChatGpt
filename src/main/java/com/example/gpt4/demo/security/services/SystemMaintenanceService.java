package com.example.gpt4.demo.security.services;

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
        
        userRepository.findUserByLogin("system")
                      .ifPresent(userRepository::delete);
        
        User user = User.builder()
                        .login("system")
                        .email("system@system.com")
                        .password(passwordEncoder.encode("system"))
                        .createdDate(LocalDateTime.now())
                        .name("System User")
                        .build();
        userRepository.save(user);
    }
}
