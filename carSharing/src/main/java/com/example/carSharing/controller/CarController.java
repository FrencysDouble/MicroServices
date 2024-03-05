package com.example.carSharing.controller;


import com.example.carSharing.model.Car;
import com.example.carSharing.service.CarService;
import com.example.carSharing.service.TokenVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final TokenVerifier tokenVerifier;

    private final CarService carService;

    public CarController(TokenVerifier tokenVerifier, CarService carService) {
        this.tokenVerifier = tokenVerifier;
        this.carService = carService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createCar(@RequestHeader("Authorization") String jwtToken, @RequestBody Car car) {
        if (tokenVerifier.verify(jwtToken))
        {
            carService.addCar(car);
            return ResponseEntity.ok("Car is Added and Token is valid");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}