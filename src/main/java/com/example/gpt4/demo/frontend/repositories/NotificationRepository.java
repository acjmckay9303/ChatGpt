package com.example.gpt4.demo.frontend.repositories;

import com.example.gpt4.demo.frontend.entity.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
    
    Flux<Notification> findAllByUserId(
        String conversationId);
    
}


