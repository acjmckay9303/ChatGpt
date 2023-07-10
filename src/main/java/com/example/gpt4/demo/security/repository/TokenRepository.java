package com.example.gpt4.demo.security.repository;

import com.example.gpt4.demo.security.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    
    Optional<Token> findTokenByToken(String login);
    
    Optional<Token> findTokenByLoginAndExpiryDateAfterAndRevokedFalse(String login, Date now);
}
