package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.TemperatureReadingRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.SensorService;
import com.example.demo.service.TemperatureReadingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TemperatureReadingServiceImpl implements TemperatureReadingService {
    private final TemperatureReadingRepository temperatureReadingRepository;
    private final SensorService sensorService;
    private final TokenRepository tokenRepository;
    
    public TemperatureReadingServiceImpl(TemperatureReadingRepository temperatureReadingRepository, 
                                       SensorService sensorService, TokenRepository tokenRepository) {
        this.temperatureReadingRepository = temperatureReadingRepository;
        this.sensorService = sensorService;
        this.tokenRepository = tokenRepository;
    }
    
    @Override
    public TemperatureReading recordReading(String sensorIdentifier, Double readingValue) {
        SensorDevice sensor = sensorService.findByIdentifier(sensorIdentifier);
        
        if (!sensor.getIsActive()) {
            throw new IllegalArgumentException("Sensor is not active");
        }
        
        TemperatureReading reading = new TemperatureReading(sensor, sensor.getColdRoom(), 
                                                           readingValue, LocalDateTime.now());
        reading = temperatureReadingRepository.save(reading);
        
        // Check for breach
        ColdRoom coldRoom = sensor.getColdRoom();
        if (readingValue < coldRoom.getMinAllowed() || readingValue > coldRoom.getMaxAllowed()) {
            createBreachAlert(sensor, coldRoom, reading, readingValue);
        }
        
        return reading;
    }
    
    @Override
    public List<TemperatureReading> getReadingsByColdRoom(Long coldRoomId) {
        return temperatureReadingRepository.findByColdRoomIdOrderByRecordedAtDesc(coldRoomId);
    }
    
    private void createBreachAlert(SensorDevice sensor, ColdRoom coldRoom, TemperatureReading reading, Double value) {
        BreachAlert alert = new BreachAlert();
        alert.setTokenNumber(UUID.randomUUID().toString().substring(0, 8));
        alert.setColdRoom(coldRoom);
        alert.setSensor(sensor);
        alert.setReading(reading);
        alert.setStatus("OPEN");
        alert.setBreachType(value < coldRoom.getMinAllowed() ? "LOW" : "HIGH");
        alert.setIssuedAt(LocalDateTime.now());
        
        tokenRepository.save(alert);
    }
}