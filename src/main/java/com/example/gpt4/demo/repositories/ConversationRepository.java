package com.example.gpt4.demo.repositories;

import com.example.gpt4.demo.entity.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ConversationRepository extends CrudRepository<Conversation, String> {
}

