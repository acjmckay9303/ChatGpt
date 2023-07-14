package com.example.gpt4.demo.frontend.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    
    @Id
    private String id;
    private String notificationType;
    private String userId;
    private String conversationId;
    private Date date;
    private Boolean seen;
}
