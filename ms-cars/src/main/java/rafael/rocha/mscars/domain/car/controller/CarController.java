package rafael.rocha.mscars.domain.car.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.exceptions.CarNotFoundException;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.mscars.domain.car.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/v1/cars")
public class CarController {


    @Autowired
    private CarService carService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/get/{carId}")
    public ResponseEntity<Object> findById(@PathVariable Long carId) {
        try {
            Car car = carService.getCarById(carId);
            return ResponseEntity.ok(car);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found!");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CarResponseDTO> createCar(@RequestBody CarRequestDTO carRequestDTO) {
        CarResponseDTO newCar = carService.createCar(carRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable Long carId, @RequestBody CarRequestDTO carRequestDTO) {
        try {
            Car updatedCar = carService.updateCar(carId, carRequestDTO);
            return ResponseEntity.ok(updatedCar);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.ok("Deleted car with id: " + carId);
    }
}