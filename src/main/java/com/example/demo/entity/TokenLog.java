package com.example.demo.entity;

import java.time.LocalDateTime;

public class TokenLog {
    private Long id;
    private String message;
    private LocalDateTime loggedAt = LocalDateTime.now();
    private Token token;

    // getters & setters
}
