package com.example.demo.service;

import com.example.demo.entity.Queue;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

    public Queue createQueue(Queue queue) {
        return queueRepository.save(queue);
    }

    public Queue getQueueById(Long id) {
        return queueRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Queue not found with id " + id));
    }

    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }

    public Queue updateQueue(Long id, Queue updatedQueue) {
        Queue queue = getQueueById(id);

        queue.setQueueName(updatedQueue.getQueueName());
        queue.setDescription(updatedQueue.getDescription());

        return queueRepository.save(queue);
    }

    public void deleteQueue(Long id) {
        Queue queue = getQueueById(id);
        queueRepository.delete(queue);
    }
}