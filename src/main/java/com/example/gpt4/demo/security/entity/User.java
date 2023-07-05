package com.example.gpt4.demo.security.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("User")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    
    @Id
    private String id;
    private String email;
    private String password;
    private String role;
}
