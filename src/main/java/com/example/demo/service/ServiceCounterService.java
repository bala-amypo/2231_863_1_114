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

    public ServiceCounter save(ServiceCounter counter) {
        return repository.save(counter);
    }

    public List<ServiceCounter> getAllCounters() {
        return repository.findAll();
    }

    public ServiceCounter getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
