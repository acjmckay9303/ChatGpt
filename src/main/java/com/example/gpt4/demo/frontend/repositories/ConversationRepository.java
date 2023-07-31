package com.example.gpt4.demo.frontend.repositories;

import com.example.gpt4.demo.frontend.entity.Conversation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ConversationRepository extends ReactiveMongoRepository<Conversation, String> {
    
    Mono<Conversation> findByUserIdAndId(
        String id,
        String userId);
    
    Flux<Conversation> findAllByUserId(String userId);
}



