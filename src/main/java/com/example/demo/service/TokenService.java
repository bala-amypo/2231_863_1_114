package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository counterRepository;
    private final TokenLogRepository logRepository;
    private final QueuePositionRepository queueRepository;

    public TokenService(
            TokenRepository tokenRepository,
            ServiceCounterRepository counterRepository,
            TokenLogRepository logRepository,
            QueuePositionRepository queueRepository) {

        this.tokenRepository = tokenRepository;
        this.counterRepository = counterRepository;
        this.logRepository = logRepository;
        this.queueRepository = queueRepository;
    }

    public Token issueToken(Long counterId) {
        ServiceCounter counter = counterRepository.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!counter.getIsActive()) {
            throw new RuntimeException("Counter not active");
        }

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());
        token.setTokenNumber("T" + System.currentTimeMillis());

        token = tokenRepository.save(token);

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(1);
        qp.setUpdatedAt(LocalDateTime.now());
        queueRepository.save(qp);

        return token;
    }

    public Token updateStatus(Long tokenId, String status) {
        Token token = getToken(tokenId);

        if ("WAITING".equals(token.getStatus()) && "SERVING".equals(status)
                || "SERVING".equals(token.getStatus()) && "COMPLETED".equals(status)) {

            token.setStatus(status);

            if ("COMPLETED".equals(status)) {
                token.setCompletedAt(LocalDateTime.now());
            }

            tokenRepository.save(token);
            return token;
        }

        throw new RuntimeException("Invalid status transition");
    }

    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
