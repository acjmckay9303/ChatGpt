package com.example.gpt4.demo.frontend.repositories;

import com.example.gpt4.demo.frontend.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
    
    Flux<Message> findAllByConversationIdOrderByTimestampAsc(String conversationId);
}

