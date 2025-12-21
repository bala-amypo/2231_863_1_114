package com.example.demo.controller;

import com.example.demo.dto.QueuePositionResponse;
import com.example.demo.entity.QueuePosition;
import com.example.demo.service.QueueService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PutMapping("/position/{tokenId}/{newPosition}")
    public QueuePositionResponse updateQueuePosition(
            @PathVariable Long tokenId,
            @PathVariable Integer newPosition) {

        QueuePosition position =
                queueService.updateQueuePosition(tokenId, newPosition);

        return new QueuePositionResponse(
                position.getToken().getTokenNumber(),
                position.getPosition(),
                position.getUpdatedAt()
        );
    }

    @GetMapping("/position/{tokenId}")
    public QueuePositionResponse getQueuePosition(@PathVariable Long tokenId) {

        QueuePosition position = queueService.getPosition(tokenId);

        return new QueuePositionResponse(
                position.getToken().getTokenNumber(),
                position.getPosition(),
                position.getUpdatedAt()
        );
    }
}
