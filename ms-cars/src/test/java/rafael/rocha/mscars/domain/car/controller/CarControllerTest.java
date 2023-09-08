package rafael.rocha.mscars.domain.car.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.exceptions.CarNotFoundException;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.mscars.domain.car.service.CarService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private ModelMapper modelMapper;

    @InjectMocks
    private CarController carController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCars() throws Exception {
        Car car = new Car();
        car.setBrand("Chevrolet");
        car.setModel("Equinox");

        when(carService.getAllCars()).thenReturn(Collections.singletonList(car));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<Car> cars = objectMapper.readValue(responseBody, new TypeReference<List<Car>>() {});
        assertEquals(1, cars.size());
        Car carResponse = cars.get(0);
        assertEquals("Chevrolet", carResponse.getBrand());
        assertEquals("Equinox", carResponse.getModel());
    }

    @Test
    void findByIdSuccessful() throws Exception {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setBrand("Chevrolet");
        car.setModel("Equinox");

        when(carService.getCarById(carId)).thenReturn(car);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/cars/get/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Car carResponse = objectMapper.readValue(responseBody, Car.class);
        assertEquals(1L, carResponse.getId());
        assertEquals("Chevrolet", carResponse.getBrand());
        assertEquals("Equinox", carResponse.getModel());
    }

    @Test
    void findByIdCarNotFound() throws Exception {
        Long carId = 1L;
        when(carService.getCarById(carId)).thenThrow(new CarNotFoundException("Couldn't find car with id: " + carId));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/cars/get/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("Car not found!"));
    }
    @Test
    void createCar() throws Exception {
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Chevrolet");
        carRequestDTO.setModel("Equinox");

        CarResponseDTO carResponseDTO = new CarResponseDTO();
        carResponseDTO.setBrand("Chevrolet");
        carResponseDTO.setModel("Equinox");

        when(carService.createCar(carRequestDTO)).thenReturn(carResponseDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        carResponseDTO = objectMapper.readValue(responseBody, CarResponseDTO.class);
        assertEquals("Chevrolet", carResponseDTO.getBrand());
        assertEquals("Equinox", carResponseDTO.getModel());
    }

    @Test
    void updateCarSuccessful() throws Exception {
        Long carId = 1L;
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Updated Brand");
        carRequestDTO.setModel("Updated Model");

        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setBrand("Updated Brand");
        updatedCar.setModel("Updated Model");

        when(carService.updateCar(carId, carRequestDTO)).thenReturn(updatedCar);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/v1/cars/update/{carId}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Car carResponse = objectMapper.readValue(responseBody, Car.class);
        assertEquals(1L, carResponse.getId());
        assertEquals("Updated Brand", carResponse.getBrand());
        assertEquals("Updated Model", carResponse.getModel());
    }

    @Test
    void testUpdateCarCarNotFound() throws Exception {
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Updated Brand");
        carRequestDTO.setModel("Updated Model");

        doThrow(new CarNotFoundException("Couldn't find car with id: 1"))
                .when(carService).updateCar(1L, carRequestDTO);

        String jsonRequest = objectMapper.writeValueAsString(carRequestDTO);

        mockMvc.perform(put("/v1/cars/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andExpect(status().isNotFound());
    }

    @Test
    void deleteCar() throws Exception {
        doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(delete("/v1/cars/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}