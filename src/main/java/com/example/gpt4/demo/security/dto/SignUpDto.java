package com.example.gpt4.demo.security.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    
    @NotEmpty
    private String email;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    private String login;
    
    @NotEmpty
    private char[] password;
}
