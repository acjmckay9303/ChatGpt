package com.example.gpt4.demo.frontend.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.mvnsearch.chatgpt.model.ChatMessageRole;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Setter
@Getter
public class Message implements Serializable {
    
    @Id
    private String id;
    
    private String conversationId;
    
    @Enumerated(EnumType.STRING)
    private ChatMessageRole role;
    
    private String content;
    
    private LocalDateTime timestamp;
    
    public Message() {
        
        this.timestamp = LocalDateTime.now();
    }
    
    public Message(ChatMessage chatMessage) {
        
        this.role = chatMessage.getRole();
        this.content = chatMessage.getContent();
        this.timestamp = LocalDateTime.now();
    }
    
    public Message(ChatCompletionResponse gptResponse) {
        
        this.role = ChatMessageRole.assistant;
        this.content = gptResponse.getReplyText();
        this.timestamp = LocalDateTime.now();
    }
}


