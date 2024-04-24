package com.example.carSharing.service;


import com.example.carSharing.CarRepository;
import com.example.carSharing.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(Car car)
    {
        carRepository.save(car);
    }

    public Car getCar(Long id)
    {
        return carRepository.findCarById(id);
    }

    public List<Car> getAllCar(){
        return carRepository.findAll();
    }
}
