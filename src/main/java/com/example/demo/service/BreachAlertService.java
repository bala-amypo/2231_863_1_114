package com.example.demo.service;

import com.example.demo.entity.BreachAlert;

public interface BreachAlertService {
    BreachAlert issueAlert(Long counterId);
    BreachAlert updateStatus(Long id, String status);
    BreachAlert getAlert(Long id);
}
