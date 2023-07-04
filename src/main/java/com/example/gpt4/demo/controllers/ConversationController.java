package com.example.gpt4.demo.controllers;

import com.example.gpt4.demo.entity.Conversation;
import com.example.gpt4.demo.repositories.ConversationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/conversations")
@CrossOrigin
public class ConversationController {
    
    private final ConversationRepository conversationRepository;
    
    public ConversationController(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
    
    @GetMapping
    public Iterable<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Conversation> getConversationById(@PathVariable("id") String id) {
        return conversationRepository.findById(id);
    }
    
    @PostMapping
    public Conversation createConversation(@RequestBody Conversation conversation) {
        return conversationRepository.save(conversation);
    }
    
    // Other CRUD operations and additional endpoints
    
}
