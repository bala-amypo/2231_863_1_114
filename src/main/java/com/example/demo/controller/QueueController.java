package com.example.demo.controller;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.Token;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueuePositionRepository queueRepository;
    private final TokenRepository tokenRepository;

    public QueueController(QueuePositionRepository queueRepository,
                           TokenRepository tokenRepository) {
        this.queueRepository = queueRepository;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/{tokenId}/{position}")
    public QueuePosition addToQueue(@PathVariable Long tokenId,
                                    @PathVariable Integer position) {

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token == null) return null;

        QueuePosition qp = new QueuePosition();
        qp.setToken(token);
        qp.setPosition(position);
        qp.setUpdatedAt(LocalDateTime.now());

        return queueRepository.save(qp);
    }

    @GetMapping
    public List<QueuePosition> getQueue() {
        return queueRepository.findAll();
    }
}
