package com.example.demo.service;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {

    private final QueuePositionRepository queueRepository;
    private final TokenRepository tokenRepository;

    public QueueService(QueuePositionRepository queueRepository,
                        TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    public QueuePosition addToQueue(Long tokenId, Integer position) {

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token == null) return null;

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(position);
        qp.setUpdatedAt(LocalDateTime.now());

        return queueRepository.save(qp);
    }

    public List<QueuePosition> getQueue() {
        return queueRepository.findAll();
    }
}
