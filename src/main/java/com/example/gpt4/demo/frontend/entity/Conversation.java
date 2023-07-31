package com.example.gpt4.demo.frontend.entity;

import lombok.Builder;
import lombok.Data;
import org.mvnsearch.chatgpt.model.ChatMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@Builder
public class Conversation {
    
    @Id
    private String id;
    
    private String name;
    
    private String userId;
    private List<Message> messages;
    
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
