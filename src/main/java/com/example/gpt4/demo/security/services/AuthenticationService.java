package com.example.gpt4.demo.security.services;

import com.example.gpt4.demo.security.dto.CredentialsDto;
import com.example.gpt4.demo.security.dto.UserDto;
import com.example.gpt4.demo.security.entities.User;
import com.example.gpt4.demo.security.exceptions.UserNotFoundException;
import com.example.gpt4.demo.security.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    public AuthenticationService(
        PasswordEncoder passwordEncoder,
        UserRepository userRepository) {
        
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    
    public UserDto authenticate(CredentialsDto credentialsDto) {
        
        User user = userRepository.findUserByLogin(credentialsDto.getLogin())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {return new UserDto(user);}
        
        throw new RuntimeException("Invalid password");
    }
    
    public UserDto findByLogin(String login) {
        
        User user = userRepository.findUserByLogin(login)
                                  .orElseThrow(() -> new UserNotFoundException("User not found", HttpStatus.NOT_FOUND));
        return new UserDto(user);
    }
}