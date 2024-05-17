package com.example.carSharing.service;

import com.example.carSharing.model.Car;
import com.example.carSharing.model.CarStatus;
import jakarta.persistence.GeneratedValue;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Profile("nodb")
@Service
public class YandexCarsService {

    private List<Car> cars = carInitialize();

    public void addCar(Car car) {
        cars.add(car);
    }

    public Car getCar(Long id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public List<Car> getAllCar() {
        return new ArrayList<>(cars);
    }

    private List<Car> carInitialize() {
        List<Car> initCar = new ArrayList<>();
        Car car1 = new Car();
        car1.setId(1L);
        car1.setName("Toyota Camry");
        car1.setLicense_plate("ABC123");
        car1.setPrice_hour(500L);
        car1.setCar_status(CarStatus.AVAILABLE);
        initCar.add(car1);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setName("Honda Civic");
        car2.setLicense_plate("XYZ789");
        car2.setPrice_hour(400L);
        car2.setCar_status(CarStatus.ORDERED);
        initCar.add(car2);

        return initCar;
    }
}
