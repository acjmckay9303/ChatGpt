package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.entity.Conversation;
import com.example.gpt4.demo.frontend.repositories.repositories.ConversationRepository;
import com.example.gpt4.demo.security.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("secure/conversation")
public class ConversationController {
    
    private final ConversationRepository conversationRepository;
    
    public ConversationController(ConversationRepository conversationRepository) {
        
        this.conversationRepository = conversationRepository;
    }
    
    @GetMapping
    public Flux<Conversation> getAllForUserConversations(Authentication authentication) {
        
        User user = (User) authentication.getPrincipal();
        return conversationRepository.findAllByUserId(user.getId());
    }
    
    @GetMapping("/{id}")
    public Mono<Conversation> getConversationById(@PathVariable("id") String id) {
        
        return conversationRepository.findById(id);
    }
    
    @PostMapping("/create")
    public Mono<Conversation> createConversation(@RequestBody Conversation conversation) {
        
        return conversationRepository.save(conversation);
    }
    
    @DeleteMapping("/delete")
    public void deleteConversation(@RequestBody Conversation conversation) {
        
        conversationRepository.delete(conversation);
    }
    
    // Other CRUD operations and additional endpoints
    
}
