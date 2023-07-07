package com.example.gpt4.demo.repositories;

import com.example.gpt4.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}

