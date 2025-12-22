package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponse {

    private Long queueId;
    private String customerName;
    private Integer queueNumber;
    private String status; // WAITING, IN_PROGRESS, COMPLETED
}
