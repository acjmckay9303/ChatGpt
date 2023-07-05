package com.example.gpt4.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("Conversation")
public class Conversation {
    @Id
    private String id;
    private List<Message> messages;
    
    private String name;
    
    public Conversation() {
        this.messages = new ArrayList<>();
    }
    
    public Conversation(String id) {
        this.id = id;
        this.messages = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public void addMessage(Message message) {
        messages.add(message);
    }
}
