package com.example.demo.controller;

import com.example.demo.dto.QueuePositionResponse;
import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@Tag(name = "Queue", description = "Queue position management endpoints")
public class QueueController {
    private final QueueService queueService;
    
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }
    
    @PutMapping("/position/{tokenId}/{newPosition}")
    @Operation(summary = "Update queue position")
    public ResponseEntity<QueuePositionResponse> updateQueuePosition(@PathVariable Long tokenId, 
                                                                   @PathVariable Integer newPosition) {
        QueuePosition queuePosition = queueService.updateQueuePosition(tokenId, newPosition);
        QueuePositionResponse response = mapToResponse(queuePosition);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/position/{tokenId}")
    @Operation(summary = "Get queue position")
    public ResponseEntity<QueuePositionResponse> getQueuePosition(@PathVariable Long tokenId) {
        QueuePosition queuePosition = queueService.getPosition(tokenId);
        QueuePositionResponse response = mapToResponse(queuePosition);
        return ResponseEntity.ok(response);
    }
    
    private QueuePositionResponse mapToResponse(QueuePosition queuePosition) {
        QueuePositionResponse response = new QueuePositionResponse();
        response.setTokenNumber(queuePosition.getToken().getTokenNumber());
        response.setPosition(queuePosition.getPosition());
        response.setUpdatedAt(queuePosition.getUpdatedAt());
        return response;
    }
}