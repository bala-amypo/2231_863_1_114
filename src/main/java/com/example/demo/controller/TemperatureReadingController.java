package com.example.demo.controller;

import com.example.demo.dto.TemperatureReadingRequest;
import com.example.demo.entity.TemperatureReading;
import com.example.demo.service.TemperatureReadingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readings")
@Tag(name = "Temperature Readings", description = "Temperature reading endpoints")
public class TemperatureReadingController {
    private final TemperatureReadingService temperatureReadingService;
    
    public TemperatureReadingController(TemperatureReadingService temperatureReadingService) {
        this.temperatureReadingService = temperatureReadingService;
    }
    
    @PostMapping
    @Operation(summary = "Record temperature reading")
    public ResponseEntity<String> recordReading(@RequestBody TemperatureReadingRequest request) {
        temperatureReadingService.recordReading(request.getSensorIdentifier(), request.getReadingValue());
        return ResponseEntity.ok("Reading recorded successfully");
    }
    
    @GetMapping("/cold-room/{coldRoomId}")
    @Operation(summary = "Get readings for cold room")
    public ResponseEntity<List<TemperatureReading>> getReadingsByColdRoom(@PathVariable Long coldRoomId) {
        List<TemperatureReading> readings = temperatureReadingService.getReadingsByColdRoom(coldRoomId);
        return ResponseEntity.ok(readings);
    }
}