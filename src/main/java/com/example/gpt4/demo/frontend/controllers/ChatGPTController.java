package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.repositories.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Slf4j
public class ChatGPTController {
    
    private final ChatGPTService chatGPTService;
    
    public ChatGPTController(
        ChatGPTService chatGPTService,
        ConversationRepository conversationRepository) {
        
        this.chatGPTService = chatGPTService;
    }
    
    @GetMapping("/stream-chat")
    public Flux<String> streamChat(@RequestParam String content) {
        
        return chatGPTService.stream(ChatCompletionRequest.of(content))
                             .map(ChatCompletionResponse::getReplyText);
    }
}
