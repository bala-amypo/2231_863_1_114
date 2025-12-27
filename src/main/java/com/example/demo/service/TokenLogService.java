package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import java.util.List;

public class TokenLogServiceImpl {

    private final TokenLogRepository repo;
    private final TokenRepository tokenRepo;

    public TokenLogServiceImpl(TokenLogRepository r, TokenRepository t) {
        this.repo = r;
        this.tokenRepo = t;
    }

    public TokenLog addLog(Long tokenId, String msg) {
        Token t = tokenRepo.findById(tokenId).orElseThrow();
        TokenLog log = new TokenLog();
        log.setToken(t);
        log.setMessage(msg);
        return repo.save(log);
    }

    public List<TokenLog> getLogs(Long tokenId) {
        return repo.findByToken_IdOrderByLoggedAtAsc(tokenId);
    }
}
