package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long expirationMillis;
    
    public JwtTokenProvider(@Value("${jwt.secret:mySecretKey}") String secretKeyString,
                           @Value("${jwt.expiration:86400000}") long expirationMillis) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        this.expirationMillis = expirationMillis;
    }
    
    public JwtTokenProvider(String secretKeyString, long expirationMillis) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        this.expirationMillis = expirationMillis;
    }
    
    public String generateToken(Long userId, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);
        
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}