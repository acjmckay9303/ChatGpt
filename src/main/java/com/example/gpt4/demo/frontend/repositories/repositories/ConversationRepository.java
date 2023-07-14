package com.example.gpt4.demo.frontend.repositories.repositories;

import com.example.gpt4.demo.frontend.entity.Conversation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConversationRepository extends ReactiveCrudRepository<Conversation, String> {
    
    Mono<Conversation> findByUserIdAndId(
        String id,
        String userId);
    
    Flux<Conversation> findAllByUserId(String userId);
}



