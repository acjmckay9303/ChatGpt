package com.example.gpt4.demo.controllers;

import com.example.gpt4.demo.dto.ChatMessage;
import com.example.gpt4.demo.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;

@Controller
public class ChatController {
    
    private final MessageService messageService;
    private final ChatGPTService chatGPTService;
    
    public ChatController(MessageService messageService, ChatGPTService chatGPTService) {
        this.messageService = messageService;
        this.chatGPTService = chatGPTService;
    }
    
    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute("chatMessage") ChatMessage chatMessage, Model model) {
        if (chatMessage != null && chatMessage.getMessage() != null && !chatMessage.getMessage().trim().isEmpty()) {
            ChatMessage userMessage = new ChatMessage("user", chatMessage.getMessage());
            messageService.saveMessage(userMessage);
            String botMessage = chatGPTService.chat(ChatCompletionRequest.of(userMessage.getMessage()))
                                              .map(ChatCompletionResponse::getReplyText)
                                              .block();
            messageService.saveMessage(new ChatMessage("bot", botMessage));
        }
        model.addAttribute("messages", messageService.getAllMessages());
        return "chat-window";
    }
    
    @GetMapping("/chatWindow")
    public String showChatWindow(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        model.addAttribute("chatMessage", new ChatMessage());
        return "chat-window";
    }
}



