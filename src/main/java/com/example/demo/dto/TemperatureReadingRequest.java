package com.example.demo.dto;

public class TemperatureReadingRequest {
    private String sensorIdentifier;
    private Double readingValue;
    
    public TemperatureReadingRequest() {}
    
    public String getSensorIdentifier() { return sensorIdentifier; }
    public void setSensorIdentifier(String sensorIdentifier) { this.sensorIdentifier = sensorIdentifier; }
    
    public Double getReadingValue() { return readingValue; }
    public void setReadingValue(Double readingValue) { this.readingValue = readingValue; }
}