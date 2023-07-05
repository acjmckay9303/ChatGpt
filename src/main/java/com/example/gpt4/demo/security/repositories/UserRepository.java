package com.example.gpt4.demo.security.repositories;

import com.example.gpt4.demo.security.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    
    public Optional<User> getUserByName(String name);
}

