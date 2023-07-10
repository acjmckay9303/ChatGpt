package com.example.gpt4.demo.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 100)
    private String login;
    
    @Column(nullable = false)
    @Size(max = 100)
    private String name;
    
    @Column(nullable = false)
    @Size(max = 100)
    private String email;
    
    @Column(nullable = false)
    @Size(max = 100)
    private String password;
    
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    
}
