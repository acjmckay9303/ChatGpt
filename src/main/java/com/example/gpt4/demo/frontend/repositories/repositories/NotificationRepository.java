package com.example.gpt4.demo.frontend.repositories.repositories;

import com.example.gpt4.demo.frontend.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    
    Iterable<Notification> findAllByUserId(
        String conversationId);
    
}


