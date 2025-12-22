package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;

    public TokenService(TokenRepository tokenRepository,
                        ServiceCounterRepository counterRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
    }

    public Token generateToken(Long counterId) {

        ServiceCounter counter =
                counterRepository.findById(counterId).orElse(null);

        if (counter == null || !Boolean.TRUE.equals(counter.getIsActive())) {
            return null;
        }

        Token token = new Token();
        token.setTokenNumber("T" + System.currentTimeMillis());
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());

        return tokenRepository.save(token);
    }

    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}

