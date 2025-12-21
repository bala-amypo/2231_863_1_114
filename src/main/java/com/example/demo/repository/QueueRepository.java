package com.example.demo.repository;

import com.example.demo.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    // You can add custom query methods here if needed
}