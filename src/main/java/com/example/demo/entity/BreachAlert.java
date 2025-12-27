package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "breach_alerts")
public class BreachAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tokenNumber;

    @ManyToOne
    @JoinColumn(name = "cold_room_id")
    private ColdRoom coldRoom;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private SensorDevice sensor;

    @ManyToOne
    @JoinColumn(name = "reading_id")
    private TemperatureReading reading;

    @Column(nullable = false)
    private String status;

    private String breachType;

    private LocalDateTime issuedAt;

    private LocalDateTime resolvedAt;

    @OneToMany(mappedBy = "breachAlert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BreachAlertLog> logs;

    public BreachAlert() {}

    public BreachAlert(
            String tokenNumber,
            ColdRoom coldRoom,
            SensorDevice sensor,
            TemperatureReading reading,
            String status,
            String breachType,
            LocalDateTime issuedAt,
            LocalDateTime resolvedAt
    ) {
        this.tokenNumber = tokenNumber;
        this.coldRoom = coldRoom;
        this.sensor = sensor;
        this.reading = reading;
        this.status = status;
        this.breachType = breachType;
        this.issuedAt = issuedAt;
        this.resolvedAt = resolvedAt;
    }

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public String getTokenNumber() {
        return tokenNumber;
    }

    public ColdRoom getColdRoom() {
        return coldRoom;
    }

    public SensorDevice getSensor() {
        return sensor;
    }

    public TemperatureReading getReading() {
        return reading;
    }

    public String getStatus() {
        return status;
    }

    public String getBreachType() {
        return breachType;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public List<BreachAlertLog> getLogs() {
        return logs;
    }

    // ===== SETTERS (REQUIRED FOR SERVICES & JPA) =====

    public void setTokenNumber(String tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public void setColdRoom(ColdRoom coldRoom) {
        this.coldRoom = coldRoom;
    }

    public void setSensor(SensorDevice sensor) {
        this.sensor = sensor;
    }

    public void setReading(TemperatureReading reading) {
        this.reading = reading;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBreachType(String breachType) {
        this.breachType = breachType;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public void setLogs(List<BreachAlertLog> logs) {
        this.logs = logs;
    }
}
