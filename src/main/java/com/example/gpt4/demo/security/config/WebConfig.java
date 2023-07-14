package com.example.gpt4.demo.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Value("${gpt.client.url}")
    String clientUrl = "http://localhost:8080"; // Replace with your frontend URL
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**")
                .allowedOrigins(clientUrl)
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
