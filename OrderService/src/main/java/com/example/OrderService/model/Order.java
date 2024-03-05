package com.example.OrderService.model;

import jakarta.persistence.*;


@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "hours")
    private String hours;
    @Column(name = "car_id")
    private Long car_id;
    @Column(name = "total_price")
    private String total_price;
}
