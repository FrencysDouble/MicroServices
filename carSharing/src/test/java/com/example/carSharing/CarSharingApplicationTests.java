package com.example.carSharing;

import com.example.carSharing.controller.CarController;
import com.example.carSharing.model.CarStatus;
import com.example.carSharing.service.CarService;
import com.example.carSharing.service.YandexCarsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.carSharing.model.Car;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarSharingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @MockBean
    private YandexCarsService yandexCarsService;

    @Test
    void shouldCreateCar() throws Exception {
        Car car = getCar();
        String carRequestString = objectMapper.writeValueAsString(car);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/car/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carRequestString)
        ).andExpect(status().isCreated());
    }

    private Car getCar() {
        Car car = new Car();
        car.setName("Toyota");
        car.setLicense_plate("1XMM32");
        car.setCar_status(CarStatus.AVAILABLE);
        return car;
    }
    @Test
    void shouldGetCarById() throws Exception {

        Car car = new Car();
        car.setId(1L);
        car.setName("Toyota");
        car.setLicense_plate("1XMM32");
        car.setCar_status(CarStatus.AVAILABLE);

        when(carService.getCar(1L)).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Toyota"));
    }

    @Test
    void shouldReturnNotFoundForNonExistingCar() throws Exception {
        when(carService.getCar(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car/get/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
