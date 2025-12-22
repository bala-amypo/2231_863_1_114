package com.example.demo.repository;

import com.example.demo.entity.TokenLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenLogRepository extends JpaRepository<TokenLog, Long> {
}
