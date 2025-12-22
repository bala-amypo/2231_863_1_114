package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "queues")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queueName;

    private String description;

    // Constructors
    public Queue() {}

    public Queue(String queueName, String description) {
        this.queueName = queueName;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
