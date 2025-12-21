package com.yourpackage.dto;

import lombok.Data;

@Data // Use Lombok to generate getters/setters automatically
public class LoginRequest {
    private String username;
    private String password;
}