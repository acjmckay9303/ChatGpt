package com.example.gpt4.demo.frontend.repositories.repositories;

import com.example.gpt4.demo.frontend.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}

