package com.example.gpt4.demo.repositories;

import com.example.gpt4.demo.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface MessageRepository extends CrudRepository<Message, String> {
}

