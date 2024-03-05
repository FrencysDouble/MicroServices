package com.example.OrderService.controller;


import com.example.OrderService.model.Car;
import com.example.OrderService.model.Order;
import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.TokenVerifier;
import jakarta.persistence.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<String> createOrder(@RequestHeader("Authorization") String jwtToken)
    {
        if (tokenVerifier.verify(jwtToken))
        {
            logger.info(carDataGet(jwtToken).getBody().toString());
            return ResponseEntity.ok("Token is valid");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not valid");
    }

    public ResponseEntity<Car> carDataGet(String jwtToken)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String authService = "http://localhost:8081/api/v1/car/get[id]";
        ResponseEntity<Car> response = restTemplate.exchange(authService, HttpMethod.GET, requestEntity, Car.class);
        return response;
    }
}
