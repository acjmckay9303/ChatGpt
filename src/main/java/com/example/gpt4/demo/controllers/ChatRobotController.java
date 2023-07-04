package com.example.gpt4.demo.controllers;

import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ChatRobotController {
    @Autowired
    private ChatGPTService chatGPTService;
    
    @PostMapping("/chat")
    @CrossOrigin(origins = "http://localhost:4200")
    public Mono<String> chat(@RequestBody String content) {
        return chatGPTService.chat(ChatCompletionRequest.of(content))
                             .map(ChatCompletionResponse::getReplyText);
    }
    
    @GetMapping("/stream-chat")
    public Flux<String> streamChat(@RequestParam String content) {
        return chatGPTService.stream(ChatCompletionRequest.of(content))
                             .map(ChatCompletionResponse::getReplyText);
    }
}