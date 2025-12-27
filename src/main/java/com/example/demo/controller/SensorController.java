package com.example.demo.controller;

import com.example.demo.dto.SensorRequest;
import com.example.demo.dto.SensorResponse;
import com.example.demo.entity.SensorDevice;
import com.example.demo.service.ColdRoomService;
import com.example.demo.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
@Tag(name = "Sensors", description = "Sensor device management endpoints")
public class SensorController {
    private final SensorService sensorService;
    private final ColdRoomService coldRoomService;
    
    public SensorController(SensorService sensorService, ColdRoomService coldRoomService) {
        this.sensorService = sensorService;
        this.coldRoomService = coldRoomService;
    }
    
    @PostMapping
    @Operation(summary = "Register new sensor")
    public ResponseEntity<SensorResponse> createSensor(@RequestBody SensorRequest request) {
        SensorDevice sensor = new SensorDevice(request.getIdentifier(), 
                                              coldRoomService.findById(request.getColdRoomId()), 
                                              request.getIsActive());
        sensor = sensorService.createSensor(sensor);
        
        SensorResponse response = mapToResponse(sensor);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update sensor status")
    public ResponseEntity<SensorResponse> updateSensorStatus(@PathVariable Long id, @RequestParam Boolean isActive) {
        SensorDevice sensor = sensorService.updateSensorStatus(id, isActive);
        SensorResponse response = mapToResponse(sensor);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all sensors")
    public ResponseEntity<List<SensorResponse>> getAllSensors() {
        List<SensorDevice> sensors = sensorService.getAllSensors();
        List<SensorResponse> responses = sensors.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    private SensorResponse mapToResponse(SensorDevice sensor) {
        SensorResponse response = new SensorResponse();
        response.setId(sensor.getId());
        response.setIdentifier(sensor.getIdentifier());
        response.setColdRoomId(sensor.getColdRoom().getId());
        response.setColdRoomName(sensor.getColdRoom().getName());
        response.setIsActive(sensor.getIsActive());
        return response;
    }
}