package com.example.demo.service;

import com.example.demo.entity.QueuePosition;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueService {

    private final QueuePositionRepository queueRepository;
    private final TokenRepository tokenRepository;

    public QueueService(
            QueuePositionRepository queueRepository,
            TokenRepository tokenRepository) {

        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    public QueuePosition updateQueuePosition(Long tokenId, Integer newPosition) {
        QueuePosition qp = queueRepository.findByToken_Id(tokenId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));

        qp.setPosition(newPosition);
        qp.setUpdatedAt(LocalDateTime.now());
        return queueRepository.save(qp);
    }

    public QueuePosition getPosition(Long tokenId) {
        return queueRepository.findByToken_Id(tokenId)
                .orElseThrow(() -> new RuntimeException("Queue not found"));
    }
}
