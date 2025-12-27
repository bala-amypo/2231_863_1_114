package com.example.demo.service.impl;

import com.example.demo.entity.Token;
import com.example.demo.entity.QueuePosition;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.QueueService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueueServiceImpl implements QueueService {
    private final QueuePositionRepository queuePositionRepository;
    private final TokenRepository tokenRepository;
    
    public QueueServiceImpl(QueuePositionRepository queuePositionRepository, TokenRepository tokenRepository) {
        this.queuePositionRepository = queuePositionRepository;
        this.tokenRepository = tokenRepository;
    }
    
    @Override
    public QueuePosition updateQueuePosition(Long tokenId, Integer newPosition) {
        if (newPosition < 1) {
            throw new IllegalArgumentException("Position must be >= 1");
        }
        
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        
        QueuePosition queuePosition = queuePositionRepository.findByTokenId(tokenId)
                .orElse(new QueuePosition());
        
        queuePosition.setToken(token);
        queuePosition.setPosition(newPosition);
        queuePosition.setUpdatedAt(LocalDateTime.now());
        
        return queuePositionRepository.save(queuePosition);
    }
    
    @Override
    public QueuePosition getPosition(Long tokenId) {
        return queuePositionRepository.findByToken_Id(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue position not found"));
    }
}