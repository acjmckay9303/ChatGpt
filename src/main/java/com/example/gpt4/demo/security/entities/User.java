package com.example.gpt4.demo.security.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document
public class User implements UserDetails {
    
    @Id
    private String id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String password;
    
    private LocalDateTime createdDate;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    
    @Override
    public String getUsername() {
        // email in our case
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        
        return true;
    }
    
}
