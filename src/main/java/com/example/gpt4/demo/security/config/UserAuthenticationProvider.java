package com.example.gpt4.demo.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gpt4.demo.security.dto.CredentialsDto;
import com.example.gpt4.demo.security.dto.UserDto;
import com.example.gpt4.demo.security.entities.Token;
import com.example.gpt4.demo.security.repository.TokenRepository;
import com.example.gpt4.demo.security.repository.UserRepository;
import com.example.gpt4.demo.security.services.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {
    
    private final AuthenticationService authenticationService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    
    public UserAuthenticationProvider(
        AuthenticationService authenticationService,
        TokenRepository tokenRepository,
        UserRepository userRepository) {
        
        this.authenticationService = authenticationService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }
    
    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public String createToken(String login) {
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour
        
        userRepository.findUserByLogin(login)
                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        String token = JWT.create()
                          .withIssuer(login)
                          .withIssuedAt(now)
                          .withExpiresAt(validity)
                          .sign(algorithm);
        
        Token tokenEntity = Token.builder()
                                 .token(token)
                                 .login(login)
                                 .revoked(false)
                                 .createdDate(LocalDateTime.now())
                                 .expiryDate(validity)
                                 .build();
        tokenRepository.save(tokenEntity);
        
        return tokenEntity.getToken();
    }
    
    public Authentication validateToken(String token) {
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        
        JWTVerifier verifier = JWT.require(algorithm)
                                  .build();
        
        DecodedJWT decoded = verifier.verify(token);
        
        Token tokenEntity = tokenRepository.findTokenByToken(token)
                                           .orElseThrow(() -> new UsernameNotFoundException("Token not found"));
        
        if (tokenEntity.getExpiryDate().before(new Date()) || tokenEntity.getRevoked()) {
            tokenEntity.setRevoked(true);
            tokenRepository.save(tokenEntity);
            throw new TokenExpiredException("Token expired", Instant.now());
        }
        
        UserDto user = authenticationService.findByLogin(decoded.getIssuer());
        
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
    
    public Authentication validateCredentials(CredentialsDto credentialsDto) {
        
        UserDto user = authenticationService.authenticate(credentialsDto);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
    
    public Boolean checkToken(String login) {
        
        Token tokenEntity = tokenRepository.findTokenByLoginAndExpiryDateAfterAndRevokedFalse(login, new Date()).get();
        return !tokenEntity.getRevoked() && tokenEntity.getExpiryDate().after(new Date());
    }
    
}