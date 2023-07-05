package com.example.gpt4.demo.security.service;

import com.example.gpt4.demo.security.dto.UserDetail;
import com.example.gpt4.demo.security.entity.User;
import com.example.gpt4.demo.security.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    public UserDetailService(UserRepository userRepository) {this.userRepository = userRepository;}
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userRepository.findById(username);
        return user.map(UserDetail::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
