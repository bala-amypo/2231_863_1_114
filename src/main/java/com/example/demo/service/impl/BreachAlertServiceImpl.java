package com.example.demo.service.impl;

import com.example.demo.entity.BreachAlert;
import com.example.demo.repository.BreachAlertRepository;
import com.example.demo.service.BreachAlertService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BreachAlertServiceImpl implements BreachAlertService {

    private final BreachAlertRepository breachAlertRepository;

    public BreachAlertServiceImpl(BreachAlertRepository breachAlertRepository) {
        this.breachAlertRepository = breachAlertRepository;
    }

    @Override
    public BreachAlert issueAlert(Long counterId) {
        BreachAlert alert = new BreachAlert();
        alert.setIssuedAt(LocalDateTime.now());
        alert.setStatus("OPEN");
        alert.setTokenNumber("TKN-" + System.currentTimeMillis());
        return breachAlertRepository.save(alert);
    }

    @Override
    public BreachAlert updateStatus(Long id, String status) {
        BreachAlert alert = breachAlertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setStatus(status);
        return breachAlertRepository.save(alert);
    }

    @Override
    public BreachAlert getAlert(Long id) {
        return breachAlertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }
}
