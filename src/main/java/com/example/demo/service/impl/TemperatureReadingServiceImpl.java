package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.TemperatureReadingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemperatureReadingServiceImpl implements TemperatureReadingService {

    private final TemperatureReadingRepository temperatureReadingRepository;
    private final SensorRepository sensorRepository;
    private final ColdRoomRepository coldRoomRepository;

    public TemperatureReadingServiceImpl(
            TemperatureReadingRepository temperatureReadingRepository,
            SensorRepository sensorRepository,
            ColdRoomRepository coldRoomRepository) {

        this.temperatureReadingRepository = temperatureReadingRepository;
        this.sensorRepository = sensorRepository;
        this.coldRoomRepository = coldRoomRepository;
    }

    @Override
    public TemperatureReading recordReading(String sensorIdentifier, Double readingValue) {

        SensorDevice sensor = sensorRepository.findByIdentifier(sensorIdentifier)
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        ColdRoom coldRoom = sensor.getColdRoom();

        TemperatureReading reading = new TemperatureReading();
        reading.setSensor(sensor);
        reading.setColdRoom(coldRoom);
        reading.setReadingValue(readingValue); // ✅ CORRECT
        reading.setRecordedAt(LocalDateTime.now());

        return temperatureReadingRepository.save(reading);
    }

    @Override
    public List<TemperatureReading> getReadingsByColdRoom(Long coldRoomId) {

        ColdRoom coldRoom = coldRoomRepository.findById(coldRoomId)
                .orElseThrow(() -> new RuntimeException("Cold room not found"));

        return temperatureReadingRepository.findByColdRoom(coldRoom);
    }
}
