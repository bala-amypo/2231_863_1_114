package com.example.demo.controller;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@Tag(name = "Tokens", description = "Token management endpoints")
public class TokenController {
    private final TokenService tokenService;
    
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    
    @PostMapping("/counter/{counterId}")
    @Operation(summary = "Issue new token")
    public ResponseEntity<Token> issueToken(@PathVariable Long counterId) {
        Token token = tokenService.issueToken(counterId);
        return ResponseEntity.ok(token);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update token status")
    public ResponseEntity<Token> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Token token = tokenService.updateStatus(id, status);
        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get token details")
    public ResponseEntity<Token> getToken(@PathVariable Long id) {
        Token token = tokenService.getToken(id);
        return ResponseEntity.ok(token);
    }
}