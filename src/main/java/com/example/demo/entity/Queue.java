package com.yourpackage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "queues")
@Data
@NoArgsConstructor
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime joinTime;

    @Column(nullable = false)
    private String status; // e.g., WAITING, SERVING, COMPLETED, CANCELLED

    // Optional: Reference to the User entity directly
    @ManyToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}