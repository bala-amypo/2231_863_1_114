package com.example.demo.repository;

import com.example.demo.entity.ColdRoom;
import com.example.demo.entity.TemperatureReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemperatureReadingRepository
        extends JpaRepository<TemperatureReading, Long> {

    List<TemperatureReading> findByColdRoom(ColdRoom coldRoom);
}
