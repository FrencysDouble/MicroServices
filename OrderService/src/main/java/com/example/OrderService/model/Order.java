package com.example.OrderService.model;

import jakarta.persistence.*;


@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "hours")
    private String hours;

    @Column(name = "total_price")
    private String total_price;
}
