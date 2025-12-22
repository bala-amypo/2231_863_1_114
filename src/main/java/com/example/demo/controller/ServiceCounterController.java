package com.example.demo.controller;

import com.example.demo.entity.ServiceCounter;
import com.example.demo.repository.ServiceCounterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counters")
public class ServiceCounterController {

    private final ServiceCounterRepository repository;

    public ServiceCounterController(ServiceCounterRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ServiceCounter addCounter(@RequestBody ServiceCounter counter) {
        return repository.save(counter);
    }

    @GetMapping
    public List<ServiceCounter> getAllCounters() {
        return repository.findAll();
    }
}
