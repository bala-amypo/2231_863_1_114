package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository serviceCounterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queuePositionRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository serviceCounterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueuePositionRepository queuePositionRepository) {
        this.tokenRepository = tokenRepository;
        this.serviceCounterRepository = serviceCounterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queuePositionRepository = queuePositionRepository;
    }

    @Override
    public Token issueToken(Long counterId) {

        ServiceCounter counter = serviceCounterRepository.findById(counterId)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());

        // 🔑 Sequential token number
        long count = tokenRepository.count() + 1;
        token.setTokenNumber("T" + count);

        token = tokenRepository.save(token);

        // 🔑 Queue position from repository
        int lastPosition = queuePositionRepository.findMaxPosition().orElse(0);

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(lastPosition + 1);
        qp.setUpdatedAt(LocalDateTime.now());

        queuePositionRepository.save(qp);

        TokenLog log = new TokenLog(token, "Token issued", LocalDateTime.now());
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        token.setStatus(status);

        if ("COMPLETED".equalsIgnoreCase(status)
                || "CANCELLED".equalsIgnoreCase(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        token = tokenRepository.save(token);

        TokenLog log = new TokenLog(token, "Status updated to " + status, LocalDateTime.now());
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }
}
