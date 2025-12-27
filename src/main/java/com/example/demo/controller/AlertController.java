package com.example.demo.controller;

import com.example.demo.dto.BreachAlertResponse;
import com.example.demo.entity.BreachAlert;
import com.example.demo.service.BreachAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
@Tag(name = "Alerts", description = "Breach alert management endpoints")
public class AlertController {

    private final BreachAlertService breachAlertService;

    public AlertController(BreachAlertService breachAlertService) {
        this.breachAlertService = breachAlertService;
    }

    @PostMapping("/counter/{counterId}")
    @Operation(summary = "Issue new breach alert")
    public ResponseEntity<BreachAlertResponse> issueAlert(@PathVariable Long counterId) {
        BreachAlert alert = breachAlertService.issueAlert(counterId);
        return ResponseEntity.ok(mapToResponse(alert));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update breach alert status")
    public ResponseEntity<BreachAlertResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        BreachAlert alert = breachAlertService.updateStatus(id, status);
        return ResponseEntity.ok(mapToResponse(alert));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get breach alert details")
    public ResponseEntity<BreachAlertResponse> getAlert(@PathVariable Long id) {
        BreachAlert alert = breachAlertService.getAlert(id);
        return ResponseEntity.ok(mapToResponse(alert));
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

        // 🚫 QUEUE POSITION REMOVED — NO CALL HERE

        return response;
    }
}
