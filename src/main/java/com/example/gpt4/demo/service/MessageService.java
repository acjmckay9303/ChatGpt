package com.example.gpt4.demo.service;

import com.example.gpt4.demo.dto.ChatMessage;
import com.example.gpt4.demo.dto.mapper.ChatMessageMapper;
import com.example.gpt4.demo.persistence.entities.Message;
import com.example.gpt4.demo.persistence.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    @Transactional
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        Message message = ChatMessageMapper.toEntity(chatMessage);
        Message savedMessage = messageRepository.save(message);
        return ChatMessageMapper.toDto(savedMessage);
    }
    
    @Transactional(readOnly = true)
    public List<ChatMessage> getAllMessages() {
        return messageRepository.findAll().stream()
                                .map(ChatMessageMapper::toDto)
                                .collect(Collectors.toList());
    }
}


