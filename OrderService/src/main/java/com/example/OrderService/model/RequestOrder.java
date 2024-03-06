package com.example.OrderService.model;

public class RequestOrder {

    private Long car_id;

    private Long hours;

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }
}
