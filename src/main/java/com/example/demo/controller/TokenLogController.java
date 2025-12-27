package com.example.demo.controller;

import com.example.demo.dto.TokenLogResponse;
import com.example.demo.entity.TokenLog;
import com.example.demo.service.TokenLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/logs")
@Tag(name = "Logs", description = "Token log management endpoints")
public class TokenLogController {
    private final TokenLogService tokenLogService;
    
    public TokenLogController(TokenLogService tokenLogService) {
        this.tokenLogService = tokenLogService;
    }
    
    @PostMapping("/{tokenId}")
    @Operation(summary = "Add log entry")
    public ResponseEntity<TokenLogResponse> addLog(@PathVariable Long tokenId, @RequestParam String message) {
        TokenLog log = tokenLogService.addLog(tokenId, message);
        TokenLogResponse response = mapToResponse(log);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{tokenId}")
    @Operation(summary = "Get logs for token")
    public ResponseEntity<List<TokenLogResponse>> getLogs(@PathVariable Long tokenId) {
        List<TokenLog> logs = tokenLogService.getLogs(tokenId);
        List<TokenLogResponse> responses = logs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    private TokenLogResponse mapToResponse(TokenLog log) {
        TokenLogResponse response = new TokenLogResponse();
        response.setId(log.getId());
        response.setTokenNumber(log.getToken().getTokenNumber());
        response.setLogMessage(log.getLogMessage());
        response.setLoggedAt(log.getLoggedAt());
        return response;
    }
}