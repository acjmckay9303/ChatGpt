package com.example.gpt4.demo.frontend.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Table(name = "conversation")
@Data
public class Conversation {
    
    @Id
    private String id;
    
    private String name;
    
    private String userId;
    private List<Message> messages;
    
    public Conversation() {
        
        this.messages = new ArrayList<>();
    }
    
    public void addMessage(Message message) {
        
        messages.add(message);
    }
    
    public List<ChatMessage> getChatMessages() {
        
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            chatMessages.add(new ChatMessage(message.getRole(), message.getContent()));
        }
        return chatMessages;
    }
}
