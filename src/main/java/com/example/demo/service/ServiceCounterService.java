package com.example.demo.service;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCounterService {

    private final ServiceCounterRepository repository;

    public ServiceCounterService(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    public ServiceCounter addCounter(ServiceCounter counter) {
        return repository.save(counter);
    }

    public List<ServiceCounter> getActiveCounters() {
        return repository.findByIsActiveTrue();
    }
}
