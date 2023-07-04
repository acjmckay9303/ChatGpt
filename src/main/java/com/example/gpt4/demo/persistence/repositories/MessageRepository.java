package com.example.gpt4.demo.persistence.repositories;

import com.example.gpt4.demo.persistence.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

