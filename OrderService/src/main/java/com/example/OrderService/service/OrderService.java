package com.example.OrderService.service;


import com.example.OrderService.OrderRepository;
import com.example.OrderService.model.Car;
import com.example.OrderService.model.Order;
import com.example.OrderService.model.RequestOrder;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Car car, RequestOrder requestOrder)
    {
        Long total_price = car.getPrice_hour() * requestOrder.getHours();
        Order order = new Order();
        order.setCar_id(car.getId());
        order.setHours(requestOrder.getHours());
        order.setTotal_price(total_price);
        orderRepository.save(order);

    }

    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }
}
