package com.example.gpt4.demo.security.repository;

import com.example.gpt4.demo.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findUserByLogin(String login);
}
