package com.example.demo.service.impl;

import com.example.demo.entity.TemperatureReading;
import com.example.demo.entity.SensorDevice;
import com.example.demo.entity.ColdRoom;
import com.example.demo.repository.TemperatureReadingRepository;
import com.example.demo.repository.SensorRepository;
import com.example.demo.repository.ColdRoomRepository;
import com.example.demo.service.TemperatureReadingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemperatureReadingServiceImpl implements TemperatureReadingService {

    private final TemperatureReadingRepository temperatureReadingRepository;
    private final SensorRepository sensorRepository;
    private final ColdRoomRepository coldRoomRepository;

    public TemperatureReadingServiceImpl(TemperatureReadingRepository temperatureReadingRepository,
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

        ColdRoom coldRoom = sensor.getColdRoom(); // assuming sensor is linked to ColdRoom

        TemperatureReading reading = new TemperatureReading();
        reading.setSensor(sensor);
        reading.setColdRoom(coldRoom);
        reading.setValue(readingValue);
        reading.setRecordedAt(LocalDateTime.now());

        return temperatureReadingRepository.save(reading);
    }

    @Override
    public List<TemperatureReading> getReadingsByColdRoom(Long coldRoomId) {
        ColdRoom coldRoom = coldRoomRepository.findById(coldRoomId)
                .orElseThrow(() -> new RuntimeException("ColdRoom not found"));

        return temperatureReadingRepository.findByColdRoom(coldRoom);
    }
}
