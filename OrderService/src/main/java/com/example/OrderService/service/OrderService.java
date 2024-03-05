package com.example.OrderService.service;


import com.example.OrderService.OrderRepository;
import com.example.OrderService.model.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order)
    {

    }
}
