package com.example.gpt4.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.mvnsearch.chatgpt.model.ChatMessageRole;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Setter
@Getter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "conversation_id")  // Add a column to store the conversation ID
    private Long conversationId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ChatMessageRole role;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "timestamp")
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


