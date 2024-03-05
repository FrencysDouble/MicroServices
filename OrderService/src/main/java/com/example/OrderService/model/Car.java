package com.example.OrderService.model;

import jakarta.persistence.*;

public class Car {
    private Long id;

    @Column(name = "car_name")
    private String name;

    @Column(name = "license_plate")
    private String license_plate;

    @Column(name = "price_per_hour")
    private String price_hour;

    @Column(name = "car_status")
    private CarStatus car_status;


    public String getPrice_hour() {
        return price_hour;
    }

    public void setPrice_hour(String price_hour) {
        this.price_hour = price_hour;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public CarStatus getCar_status() {
        return car_status;
    }

    public void setCar_status(CarStatus car_status) {
        this.car_status = car_status;
    }
}

