package com.example.gpt4.demo.repositories;

import com.example.gpt4.demo.entity.Conversation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, String> {

}


