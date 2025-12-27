package com.example.demo.entity;

import java.time.LocalDateTime;

public class Token {
    private Long id;
    private String tokenNumber;
    private String status;
    private LocalDateTime issuedAt = LocalDateTime.now();
    private LocalDateTime completedAt;
    private ServiceCounter serviceCounter;

    // getters & setters
}
