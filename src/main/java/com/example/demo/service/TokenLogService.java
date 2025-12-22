package com.example.demo.service;

import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenLogService {

    private final TokenLogRepository logRepository;
    private final TokenRepository tokenRepository;

    public TokenLogService(TokenLogRepository logRepository,
                           TokenRepository tokenRepository) {
        this.logRepository = logRepository;
        this.tokenRepository = tokenRepository;
    }

    public TokenLog addLog(Long tokenId, TokenLog log) {

        Token token = tokenRepository.findById(tokenId).orElse(null);
        if (token == null) return null;

        log.setToken(token);
        return logRepository.save(log);
    }

    public List<TokenLog> getAllLogs() {
        return logRepository.findAll();
    }
}
