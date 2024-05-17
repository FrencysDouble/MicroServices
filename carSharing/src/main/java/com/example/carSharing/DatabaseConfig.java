package com.example.carSharing;

import com.example.carSharing.service.CarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("db")
public class DatabaseConfig {

    @Bean
    public CarService carService(CarRepository carRepository) {
        return new CarService(carRepository);
    }
}
