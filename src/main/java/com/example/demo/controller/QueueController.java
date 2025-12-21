package com.yourpackage.controller;

import com.yourpackage.entity.Queue;
import com.yourpackage.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    // Users can join the queue
    @PostMapping("/join")
    public ResponseEntity<Queue> joinQueue(@RequestParam Long userId) {
        return ResponseEntity.ok(queueService.addToQueue(userId));
    }

    // Users check their current position
    @GetMapping("/status/{userId}")
    public ResponseEntity<?> getStatus(@PathVariable Long userId) {
        int position = queueService.getPosition(userId);
        return ResponseEntity.ok("Your current position is: " + position);
    }

    // Admin only: Move to the next person in line
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/next")
    public ResponseEntity<Queue> processNext() {
        return ResponseEntity.ok(queueService.serveNextCustomer());
    }

    // Admin only: Get all active people in queue
    @GetMapping("/active")
    public ResponseEntity<List<Queue>> getActiveQueue() {
        return ResponseEntity.ok(queueService.getAllActive());
    }
}