package com.example.carSharing;

import com.example.carSharing.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {

    Car findCarById(Long id);
    List<Car> findAll();
}
