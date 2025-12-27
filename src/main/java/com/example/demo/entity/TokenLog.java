package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TokenLog {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Token token;

    private String logMessage;
    private LocalDateTime loggedAt;

    @PrePersist
    public void onCreate() {
        loggedAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public Token getToken() {
        return token;
    }
 
    public void setToken(Token token) {
        this.token = token;
    }
 
    public String getLogMessage() {
        return logMessage;
    }
 
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
 
    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }
 
    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }
}
