package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.entity.Conversation;
import com.example.gpt4.demo.frontend.entity.Message;
import com.example.gpt4.demo.frontend.repositories.ConversationRepository;
import com.example.gpt4.demo.frontend.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/secure/conversation")
@RequiredArgsConstructor
public class ConversationController {
    
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ChatGPTService chatGPTService;
    
    @PostMapping("/conversations/create")
    public Mono<Conversation> createConversations(
        Authentication authentication,
        @RequestParam String name) {
        
        try {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            Conversation conversation = Conversation.builder()
                                                    .name(name)
                                                    .userId(user.getUsername()).build();
            return conversationRepository.save(conversation);
        } catch (Exception e) {
            return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
    
    @PostMapping("/messages/send")
    public Mono<Message> sendMessage(
        @RequestParam String conversationId,
        @RequestBody Message message) {
        
        return conversationRepository
                   .findById(conversationId)
                   .flatMap(conversation -> {
                       ChatCompletionRequest request = new ChatCompletionRequest();
                       conversation.addMessage(message);
                       request.setMessages(conversation.getChatMessages());
                       return chatGPTService
                                  .chat(request)
                                  .flatMap(response -> {
                                      Message gptMessage = new Message(response);
                                      conversation.addMessage(gptMessage);
                                      return conversationRepository.save(conversation)
                                                                   .thenReturn(gptMessage);
                                  });
                   })
                   .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                       "Conversation not found")))
                   .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus
                                                                                  .INTERNAL_SERVER_ERROR,
                       e.getMessage())));
        
    }
    
    @GetMapping(value = "/conversations/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Conversation> streamConversations(Authentication authentication) {
        
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return conversationRepository.findAllByUserId(user.getUsername());
    }
    
    @GetMapping(value = "/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> streamMessages(@RequestParam String conversationId) {
        
        return messageRepository.findAllByConversationIdOrderByTimestampAsc(conversationId);
    }
}

