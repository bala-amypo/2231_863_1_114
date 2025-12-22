package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.ServiceCounterRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;

    public TokenController(TokenRepository tokenRepository,
                           ServiceCounterRepository counterRepository) {
        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
    }

    @PostMapping("/{counterId}")
    public Token generateToken(@PathVariable Long counterId) {

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

    @GetMapping
    public List<Token> getAllTokens() {
        return tokenRepository.findAll();
    }
}
