    package com.yourpackage.repository;

import com.yourpackage.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

    // Find all people currently waiting, sorted by earliest join time
    List<Queue> findByStatusOrderByJoinTimeAsc(String status);

    // Get the very first person in the WAITING line
    Optional<Queue> findFirstByStatusOrderByJoinTimeAsc(String status);
    
    // Find a specific user's queue entry
    Optional<Queue> findByUserIdAndStatus(Long userId, String status);
}