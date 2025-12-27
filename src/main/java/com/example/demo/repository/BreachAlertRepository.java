package com.example.demo.repository;

import com.example.demo.entity.BreachAlert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreachAlertRepository extends CrudRepository<BreachAlert, Long> {
}
