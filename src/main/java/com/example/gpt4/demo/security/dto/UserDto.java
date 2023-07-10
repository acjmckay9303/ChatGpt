package com.example.gpt4.demo.security.dto;

import com.example.gpt4.demo.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    private String login;
    private String email;
    private String name;
    
    public UserDto(User user){
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}