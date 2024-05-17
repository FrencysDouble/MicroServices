package com.example.carSharing.controller;


import com.example.carSharing.model.Car;
import com.example.carSharing.service.CarService;
import com.example.carSharing.service.YandexCarsService;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;
    private final YandexCarsService yandexCarsService;

    private final Environment environment;

    @Autowired
    public CarController(CarService carService, YandexCarsService yandexCarsService, Environment environment) {
        this.carService = carService;
        this.yandexCarsService = yandexCarsService;
        this.environment = environment;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCar(@RequestBody Car car) {
        if (isDbProfileActive()) {
            carService.addCar(car);
        } else {
            yandexCarsService.addCar(car);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Car is Added and Token is valid");
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        Car car = isDbProfileActive() ? carService.getCar(id) : yandexCarsService.getCar(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Car>> getAllCar() {
        List<Car> cars = isDbProfileActive() ? carService.getAllCar() : yandexCarsService.getAllCar();
        if (cars != null) {
            return ResponseEntity.ok(cars);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isDbProfileActive() {
        return Arrays.asList(environment.getActiveProfiles()).contains("db");

    }
}
