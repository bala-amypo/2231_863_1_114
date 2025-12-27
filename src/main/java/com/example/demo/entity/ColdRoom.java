package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cold_rooms")
public class ColdRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String location;
    private Double minAllowed;
    private Double maxAllowed;
    
    @OneToMany(mappedBy = "coldRoom", cascade = CascadeType.ALL)
    private List<SensorDevice> sensors;
    
    @OneToMany(mappedBy = "coldRoom", cascade = CascadeType.ALL)
    private List<TemperatureReading> readings;
    
    @OneToMany(mappedBy = "coldRoom", cascade = CascadeType.ALL)
    private List<BreachAlert> alerts;
    
    public ColdRoom() {}
    
    public ColdRoom(String name, String location, Double minAllowed, Double maxAllowed) {
        this.name = name;
        this.location = location;
        this.minAllowed = minAllowed;
        this.maxAllowed = maxAllowed;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public Double getMinAllowed() { return minAllowed; }
    public void setMinAllowed(Double minAllowed) { this.minAllowed = minAllowed; }
    
    public Double getMaxAllowed() { return maxAllowed; }
    public void setMaxAllowed(Double maxAllowed) { this.maxAllowed = maxAllowed; }
    
    public List<SensorDevice> getSensors() { return sensors; }
    public void setSensors(List<SensorDevice> sensors) { this.sensors = sensors; }
    
    public List<TemperatureReading> getReadings() { return readings; }
    public void setReadings(List<TemperatureReading> readings) { this.readings = readings; }
    
    public List<BreachAlert> getAlerts() { return alerts; }
    public void setAlerts(List<BreachAlert> alerts) { this.alerts = alerts; }
}