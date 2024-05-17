package com.example.carSharing.controller;


import com.example.carSharing.model.Car;
import com.example.carSharing.service.CarService;
import com.example.carSharing.service.YandexCarsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    private final YandexCarsService yandexCarsService;

    public CarController(CarService carService, YandexCarsService yandexCarsService) {
        this.carService = carService;
        this.yandexCarsService = yandexCarsService;
        ;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCar(@RequestBody Car car) {
            carService.addCar(car);
            return ResponseEntity.status(HttpStatus.CREATED).body("Car is Added and Token is valid");
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Car> getCar(@PathVariable Long id)
    {
        Car car = carService.getCar(id);
        if (car != null) {
            return ResponseEntity.ok(car);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Car>> getAllCar()
    {
        List<Car> cars = carService.getAllCar();
        if (cars != null)
        {
            return ResponseEntity.ok(cars);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getTest")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Car>> getCarTest() {

        List<Car> cars = yandexCarsService.getAllCar();

        return ResponseEntity.accepted().body(cars);
    }


    @GetMapping("/getOne/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Car> getCarById(@PathVariable Long id)
    {
        Car car = yandexCarsService.getCar(id);
        if (car == null)
        {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.accepted().body(car);
    }


}