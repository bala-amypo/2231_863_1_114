package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import java.time.LocalDateTime;
import java.util.*;

public class TokenServiceImpl {

    private final TokenRepository tokenRepo;
    private final ServiceCounterRepository counterRepo;
    private final TokenLogRepository logRepo;
    private final QueuePositionRepository queueRepo;

    public TokenServiceImpl(TokenRepository t, ServiceCounterRepository c,
                            TokenLogRepository l, QueuePositionRepository q) {
        this.tokenRepo = t;
        this.counterRepo = c;
        this.logRepo = l;
        this.queueRepo = q;
    }

    public Token issueToken(Long counterId) {
        ServiceCounter sc = counterRepo.findById(counterId)
                .orElseThrow(() -> new RuntimeException("Counter not found"));

        if (!sc.getIsActive())
            throw new IllegalArgumentException("Counter not active");

        Token t = new Token();
        t.setStatus("WAITING");
        t.setServiceCounter(sc);
        t.setTokenNumber(sc.getCounterName() + "-" + System.currentTimeMillis());

        Token saved = tokenRepo.save(t);

        QueuePosition qp = new QueuePosition();
        qp.setToken(saved);
        qp.setPosition(tokenRepo
                .findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING").size());

        queueRepo.save(qp);

        TokenLog log = new TokenLog();
        log.setToken(saved);
        log.setMessage("Issued");
        logRepo.save(log);

        return saved;
    }

    public Token updateStatus(Long id, String status) {
        Token t = tokenRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (t.getStatus().equals("WAITING") && status.equals("COMPLETED"))
            throw new IllegalArgumentException("Invalid status");

        t.setStatus(status);
        if (status.equals("COMPLETED") || status.equals("CANCELLED"))
            t.setCompletedAt(LocalDateTime.now());

        tokenRepo.save(t);

        TokenLog log = new TokenLog();
        log.setToken(t);
        log.setMessage(status);
        logRepo.save(log);

        return t;
    }

    public Token getToken(Long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
