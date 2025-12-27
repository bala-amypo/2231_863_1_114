package com.example.demo.entity;

import java.time.LocalDateTime;

public class QueuePosition {

    private Long id;
    private Integer position;
    private Token token;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
