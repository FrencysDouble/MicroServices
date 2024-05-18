package com.example.OrderService.controller;


import com.example.OrderService.model.Car;
import com.example.OrderService.model.Order;
import com.example.OrderService.model.RequestOrder;
import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.TokenVerifier;
import jakarta.persistence.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final TokenVerifier tokenVerifier;

    private final OrderService orderService;

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(TokenVerifier tokenVerifier, OrderService orderService, RestTemplate restTemplate) {
        this.tokenVerifier = tokenVerifier;
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createOrder(@RequestBody RequestOrder requestOrder) {
        Car car = carDataGet(requestOrder.getCar_id()).getBody();
        if (car != null) {
            orderService.addOrder(car, requestOrder);
        }

        return ResponseEntity.ok("OK");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Car> carDataGet(Long car_id) {
        String apiGatewayUrl = "http://carshare-service:8082/api/v1/car/get";

        String url = apiGatewayUrl + "/" + car_id;

        ResponseEntity<Car> response = restTemplate.getForEntity(url, Car.class);
        return response;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Order>> getAllOrder()
    {
        try{
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
