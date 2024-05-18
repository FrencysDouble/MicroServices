package com.example.OrderService;

import com.example.OrderService.model.Car;
import com.example.OrderService.model.CarStatus;
import com.example.OrderService.model.Order;
import com.example.OrderService.model.RequestOrder;
import com.example.OrderService.service.OrderService;
import com.example.OrderService.service.TokenVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private TokenVerifier tokenVerifier;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    //Component Tests
    @Test
    void shouldCreateOrder() throws Exception {
        Car car = new Car();
        car.setId(1L);
        car.setName("Toyota");
        car.setLicense_plate("1XMM32");
        car.setCar_status(CarStatus.AVAILABLE);

        given(restTemplate.getForEntity(eq("http://carshare-service:8082/api/v1/car/get/1"), eq(Car.class)))
                .willReturn(new ResponseEntity<>(car, HttpStatus.OK));

        Order requestOrder = new Order();
        requestOrder.setCar_id(1L);
        requestOrder.setHours(5L);
        requestOrder.setTotal_price(500L);

        String requestOrderJson = objectMapper.writeValueAsString(requestOrder);

        mockMvc.perform(post("/api/v1/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestOrderJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllOrders() throws Exception {
        Order order1 = new Order();
        order1.setCar_id(1L);
        order1.setHours(5L);
        order1.setTotal_price(500L);

        Order order2 = new Order();
        order2.setCar_id(2L);
        order2.setHours(3L);
        order2.setTotal_price(300L);

        given(orderService.getAllOrders()).willReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/api/v1/order/get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].car_id").value(1L))
                .andExpect(jsonPath("$[0].hours").value(5L))
                .andExpect(jsonPath("$[0].total_price").value(500L))
                .andExpect(jsonPath("$[1].car_id").value(2L))
                .andExpect(jsonPath("$[1].hours").value(3L))
                .andExpect(jsonPath("$[1].total_price").value(300L));
    }
}

