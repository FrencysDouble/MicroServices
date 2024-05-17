package com.example.carSharing;


import com.example.carSharing.service.CarService;
import com.example.carSharing.service.YandexCarsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("nodb")
public class NoDatabaseConfig {

    @Bean
    public YandexCarsService YandexCarService() {
        return new YandexCarsService();
    }
}
