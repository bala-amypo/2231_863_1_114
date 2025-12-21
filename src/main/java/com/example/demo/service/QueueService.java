package com.yourpackage.service;

import com.yourpackage.entity.Queue;
import com.yourpackage.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public Queue addToQueue(Long userId) {
        Queue entry = new Queue();
        entry.setUserId(userId);
        entry.setJoinTime(LocalDateTime.now());
        entry.setStatus("WAITING");
        return queueRepository.save(entry);
    }

    public int getPosition(Long userId) {
        List<Queue> waitingList = queueRepository.findByStatusOrderByJoinTimeAsc("WAITING");
        for (int i = 0; i < waitingList.size(); i++) {
            if (waitingList.get(i).getUserId().equals(userId)) {
                return i + 1; // Position 1, 2, 3...
            }
        }
        return -1; // Not in queue
    }

    public Queue serveNextCustomer() {
        Queue next = queueRepository.findFirstByStatusOrderByJoinTimeAsc("WAITING")
                .orElseThrow(() -> new RuntimeException("No one in queue"));
        
        next.setStatus("SERVED");
        return queueRepository.save(next);
    }

    public List<Queue> getAllActive() {
        return queueRepository.findByStatusOrderByJoinTimeAsc("WAITING");
    }
}