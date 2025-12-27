package com.example.demo.service.impl;

import com.example.demo.entity.BreachAlert;
import com.example.demo.entity.TemperatureReading;
import com.example.demo.repository.BreachAlertRepository;
import com.example.demo.service.TemperatureReadingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TemperatureReadingServiceImpl implements TemperatureReadingService {

    private final BreachAlertRepository breachAlertRepository;

    public TemperatureReadingServiceImpl(BreachAlertRepository breachAlertRepository) {
        this.breachAlertRepository = breachAlertRepository;
    }

    @Override
    public BreachAlert createAlert(TemperatureReading reading) {
        BreachAlert alert = new BreachAlert();
        alert.setReading(reading);
        alert.setIssuedAt(LocalDateTime.now());
        alert.setStatus("OPEN");
        alert.setTokenNumber("TKN-" + System.currentTimeMillis());
        alert.setBreachType("TEMPERATURE_BREACH"); // set according to your logic
        alert.setColdRoom(reading.getColdRoom());
        alert.setSensor(reading.getSensor());

        return breachAlertRepository.save(alert);
    }

    @Override
    public BreachAlert updateAlertStatus(Long alertId, String status) {
        BreachAlert alert = breachAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setStatus(status);
        if ("RESOLVED".equalsIgnoreCase(status)) {
            alert.setResolvedAt(LocalDateTime.now());
        }
        return breachAlertRepository.save(alert);
    }

    @Override
    public BreachAlert getAlert(Long alertId) {
        return breachAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }
}
