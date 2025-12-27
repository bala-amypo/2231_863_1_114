package com.example.demo.repository;

import com.example.demo.entity.QueuePosition;
import java.util.Optional;

public interface QueuePositionRepository {
    Optional<QueuePosition> findByToken_Id(Long tokenId);
    QueuePosition save(QueuePosition qp);
}
