package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "breach_alert_logs")
public class BreachAlertLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "breach_alert_id")
    private BreachAlert breachAlert;

    public BreachAlertLog() {}

    public BreachAlertLog(String message, LocalDateTime createdAt, BreachAlert breachAlert) {
        this.message = message;
        this.createdAt = createdAt;
        this.breachAlert = breachAlert;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BreachAlert getBreachAlert() { return breachAlert; }
}
