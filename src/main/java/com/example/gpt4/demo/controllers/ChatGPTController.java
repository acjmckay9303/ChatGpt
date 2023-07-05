package com.example.gpt4.demo.controllers;

import com.example.gpt4.demo.entity.Conversation;
import com.example.gpt4.demo.entity.Message;
import com.example.gpt4.demo.exceptions.ConversationNotFoundException;
import com.example.gpt4.demo.repositories.ConversationRepository;
import com.example.gpt4.demo.repositories.MessageRepository;
import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController("/secure")
@CrossOrigin
public class ChatGPTController {
    
    private final ChatGPTService chatGPTService;
    
    private final ConversationRepository conversationRepository;
    
    public ChatGPTController(
        ChatGPTService chatGPTService,
        ConversationRepository conversationRepository,
        MessageRepository messageRepository) {
        this.chatGPTService = chatGPTService;
        this.conversationRepository = conversationRepository;
    }
    
    @PostMapping("/conversation/start")
    public String startConversation() {
        // Generate a UUID for the conversation ID
        String conversationId = UUID.randomUUID().toString();
        
        // Create a new conversation and save it to the repository
        Conversation conversation = new Conversation(conversationId);
        Conversation savedConversationMono = conversationRepository.save(conversation);
        
        // Return the conversation ID as the response
        return savedConversationMono.getId();
    }
    
    @PostMapping("/conversation/{conversationId}/chat")
    public Mono<String> chat(@PathVariable String conversationId, @RequestBody List<ChatMessage> messages) {
        ChatCompletionRequest request = new ChatCompletionRequest();
        request.setMessages(messages);
        
        // Retrieve the conversation from the repository
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        
        // Add the new message to the conversation
        ChatMessage lastMessage = messages.get(messages.size() - 1);
    
        Mono<ChatCompletionResponse> response = chatGPTService.chat(request);
        
        Message gptMessage = new Message(response.block());
        Message userMessage = new Message(lastMessage);
        conversation.get().addMessage(userMessage);
        conversation.get().addMessage(gptMessage);
        conversationRepository.save(conversation.get());
        
        // Return the chat response
        return response
                   .map(ChatCompletionResponse::getReplyText);
    }
    
    @GetMapping("/stream-chat")
    public Flux<String> streamChat(@RequestParam String content) {
        return chatGPTService.stream(ChatCompletionRequest.of(content))
                             .map(ChatCompletionResponse::getReplyText);
    }
}
