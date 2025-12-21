package com.yourpackage.controller;

import com.yourpackage.dto.LoginRequest;
import com.yourpackage.dto.SignupRequest;
import com.yourpackage.dto.JwtResponse;
import com.yourpackage.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String jwt = authService.login(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        authService.register(signupRequest);
        return ResponseEntity.ok("User registered successfully!");
    }
}