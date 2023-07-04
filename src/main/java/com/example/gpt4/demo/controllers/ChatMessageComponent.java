package com.example.gpt4.demo.controllers;

import com.example.gpt4.demo.dto.ChatMessage;
import com.example.gpt4.demo.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatMessageComponent {
    
    private final MessageService messageService;
    
    public ChatMessageComponent(MessageService messageService) {this.messageService = messageService;}
    
    @GetMapping("/chatMessage")
    public String getChatMessage(@RequestParam("message") String message, Model model) {
        model.addAttribute("message", message);
        return "chat-message"; // returns the chat-message.html Thymeleaf view
    }
    
    @GetMapping("/chatMessageWindow")
    public String showChatWindow(Model model) {
        model.addAttribute("messages", messageService.getAllMessages()); // replace with your service method
        model.addAttribute("chatMessage", new ChatMessage());
        return "chat-window";
    }
    
}

