package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class TokenLogController {

    private final TokenLogRepository logRepository;
    private final TokenRepository tokenRepository;

    public TokenLogController(TokenLogRepository logRepository,
                              TokenRepository tokenRepository) {
        this.logRepository = logRepository;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/{tokenId}")
    public TokenLog addLog(@PathVariable Long tokenId,
                           @RequestBody TokenLog log) {

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token == null) return null;

        log.setToken(token);
        return logRepository.save(log);
    }

    @GetMapping
    public List<TokenLog> getAllLogs() {
        return logRepository.findAll();
    }
}
UserRepository.java          ✅
ServiceCounterRepository.java
TokenRepository.java
QueuePositionRepository.java
TokenLogRepository.java
