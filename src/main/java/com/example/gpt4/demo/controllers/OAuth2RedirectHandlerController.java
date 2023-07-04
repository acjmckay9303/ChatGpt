package com.example.gpt4.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2RedirectHandlerController {
    
    @GetMapping("/login/oauth2/code/github")
    public String handleOAuth2Redirect() {
        // You would typically check if the login was successful and handle errors here.
        // For now, we'll just redirect to the chat window.
        return "redirect:/chatWindow";
    }
}
