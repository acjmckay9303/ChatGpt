package com.example.gpt4.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "conversation")
@Data
public class Conversation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "conversation_id")
    private List<Message> messages;
    
    public Conversation() {
        this.messages = new ArrayList<>();
    }
    
    public void addMessage(Message message) {
        messages.add(message);
    }
}
