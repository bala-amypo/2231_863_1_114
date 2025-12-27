package com.example.demo.entity;

import java.time.LocalDateTime;

public class Token {

    private Long id;
    private String tokenNumber;
    private String status;
    private LocalDateTime issuedAt;
    private ServiceCounter serviceCounter;

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setServiceCounter(ServiceCounter serviceCounter) {
        this.serviceCounter = serviceCounter;
    }
}
