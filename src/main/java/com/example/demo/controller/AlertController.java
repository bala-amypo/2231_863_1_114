package com.example.demo.controller;

import com.example.demo.dto.BreachAlertResponse;
import com.example.demo.entity.BreachAlert;
import com.example.demo.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
@Tag(name = "Alerts", description = "Breach alert management endpoints")
public class AlertController {
    private final TokenService tokenService;
    
    public AlertController(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    
    @PostMapping("/counter/{counterId}")
    @Operation(summary = "Issue new alert token")
    public ResponseEntity<BreachAlertResponse> issueToken(@PathVariable Long counterId) {
        BreachAlert token = tokenService.issueToken(counterId);
        BreachAlertResponse response = mapToResponse(token);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "Update alert status")
    public ResponseEntity<BreachAlertResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        BreachAlert token = tokenService.updateStatus(id, status);
        BreachAlertResponse response = mapToResponse(token);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get alert details")
    public ResponseEntity<BreachAlertResponse> getAlert(@PathVariable Long id) {
        BreachAlert token = tokenService.getToken(id);
        BreachAlertResponse response = mapToResponse(token);
        return ResponseEntity.ok(response);
    }
    
    private BreachAlertResponse mapToResponse(BreachAlert alert) {
        BreachAlertResponse response = new BreachAlertResponse();
        response.setId(alert.getId());
        response.setTokenNumber(alert.getTokenNumber());
        response.setStatus(alert.getStatus());
        response.setBreachType(alert.getBreachType());
        response.setIssuedAt(alert.getIssuedAt());
        response.setResolvedAt(alert.getResolvedAt());
        
        if (alert.getColdRoom() != null) {
            response.setColdRoomName(alert.getColdRoom().getName());
        }
        if (alert.getSensor() != null) {
            response.setSensorIdentifier(alert.getSensor().getIdentifier());
        }
        if (alert.getQueuePosition() != null) {
            response.setQueuePosition(alert.getQueuePosition().getPosition());
        }
        
        return response;
    }
}