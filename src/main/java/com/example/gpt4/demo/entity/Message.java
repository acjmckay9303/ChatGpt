package com.example.gpt4.demo.entity;

import lombok.Setter;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.mvnsearch.chatgpt.model.ChatMessageRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("Message")
@Setter
public class Message {
    @Id
    private String id;
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
    
    public Message(ChatMessageRole role, String content) {
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public ChatMessageRole getRole() {
        return role;
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
}

