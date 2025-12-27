package com.example.demo.service;

import com.example.demo.entity.TemperatureReading;
import java.util.List;

public interface TemperatureReadingService {
    TemperatureReading recordReading(String sensorIdentifier, Double readingValue);
    List<TemperatureReading> getReadingsByColdRoom(Long coldRoomId);
}