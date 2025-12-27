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
    
    @Column(unique = true)
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
    
    private String status;
    private String breachType;
    private LocalDateTime issuedAt;
    private LocalDateTime resolvedAt;
    
    @OneToOne(mappedBy = "token", cascade = CascadeType.ALL)
    private QueuePosition queuePosition;
    
    @OneToMany(mappedBy = "token", cascade = CascadeType.ALL)
    private List<TokenLog> logs;
    
    public BreachAlert() {}
    
    public BreachAlert(String tokenNumber, ColdRoom coldRoom, SensorDevice sensor, TemperatureReading reading, String status, String breachType, LocalDateTime issuedAt, LocalDateTime resolvedAt) {
        this.tokenNumber = tokenNumber;
        this.coldRoom = coldRoom;
        this.sensor = sensor;
        this.reading = reading;
        this.status = status;
        this.breachType = breachType;
        this.issuedAt = issuedAt;
        this.resolvedAt = resolvedAt;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTokenNumber() { return tokenNumber; }
    public void setTokenNumber(String tokenNumber) { this.tokenNumber = tokenNumber; }
    
    public ColdRoom getColdRoom() { return coldRoom; }
    public void setColdRoom(ColdRoom coldRoom) { this.coldRoom = coldRoom; }
    
    public SensorDevice getSensor() { return sensor; }
    public void setSensor(SensorDevice sensor) { this.sensor = sensor; }
    
    public TemperatureReading getReading() { return reading; }
    public void setReading(TemperatureReading reading) { this.reading = reading; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getBreachType() { return breachType; }
    public void setBreachType(String breachType) { this.breachType = breachType; }
    
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
    
    public QueuePosition getQueuePosition() { return queuePosition; }
    public void setQueuePosition(QueuePosition queuePosition) { this.queuePosition = queuePosition; }
    
    public List<TokenLog> getLogs() { return logs; }
    public void setLogs(List<TokenLog> logs) { this.logs = logs; }
}