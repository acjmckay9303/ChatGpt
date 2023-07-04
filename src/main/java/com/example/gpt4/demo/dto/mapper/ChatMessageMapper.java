package com.example.gpt4.demo.dto.mapper;

import com.example.gpt4.demo.dto.ChatMessage;
import com.example.gpt4.demo.persistence.entities.Message;

public class ChatMessageMapper {
    
    public static ChatMessage toDto(Message message) {
        ChatMessage dto = new ChatMessage();
        dto.setSender(message.getSender());
        dto.setMessage(message.getContent());
        return dto;
    }
    
    public static Message toEntity(ChatMessage dto) {
        Message message = new Message();
        message.setSender(dto.getSender());
        message.setContent(dto.getMessage());
        return message;
    }
}

