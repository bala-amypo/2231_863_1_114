package com.example.demo.service.impl;

import com.example.demo.entity.SensorDevice;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SensorRepository;
import com.example.demo.service.SensorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    
    public SensorServiceImpl(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    
    @Override
    public SensorDevice createSensor(SensorDevice sensor) {
        if (sensorRepository.findByIdentifier(sensor.getIdentifier()).isPresent()) {
            throw new IllegalArgumentException("Sensor identifier already exists");
        }
        
        return sensorRepository.save(sensor);
    }
    
    @Override
    public SensorDevice updateSensorStatus(Long id, Boolean isActive) {
        SensorDevice sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found"));
        
        sensor.setIsActive(isActive);
        return sensorRepository.save(sensor);
    }
    
    @Override
    public List<SensorDevice> getAllSensors() {
        return sensorRepository.findAll();
    }
    
    @Override
    public SensorDevice findByIdentifier(String identifier) {
        return sensorRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor not found"));
    }
}