package com.example.demo.entity;

public class TokenLog {

    private Long id;
    private String message;
    private Token token;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Token getToken() { return token; }
    public void setToken(Token token) { this.token = token; }
}
