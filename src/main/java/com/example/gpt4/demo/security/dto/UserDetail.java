package com.example.gpt4.demo.security.dto;

import com.example.gpt4.demo.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {
    private String name;
    private String email;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;
    
    public UserDetail(User user){
        this.name = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.grantedAuthorities = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return grantedAuthorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return name;
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
