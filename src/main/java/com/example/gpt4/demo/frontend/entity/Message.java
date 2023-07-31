package com.example.gpt4.demo.frontend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.model.ChatMessageRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document
@Setter
@Getter
@AllArgsConstructor
public class Message implements Serializable {
    
    @Id
    private String id;
    private String conversationId;
    private ChatMessageRole role;
    private String content;
    private LocalDateTime timestamp;
    
    public Message(ChatCompletionResponse gptResponse) {
        
        this.role = ChatMessageRole.assistant;
        this.content = gptResponse.getReplyText();
        this.timestamp = LocalDateTime.now();
    }
}


