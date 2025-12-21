package com.yourpackage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponse {
    private Long queueId;
    private String customerName;
    private int position;
    private String status;
    private LocalDateTime estimatedServiceTime;
}