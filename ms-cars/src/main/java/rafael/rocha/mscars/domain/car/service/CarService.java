package rafael.rocha.mscars.domain.car.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.exceptions.CarNotFoundException;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.mscars.domain.car.repository.CarRepository;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + carId));
    }

    public CarResponseDTO createCar(CarRequestDTO carRequestDTO) {
        Car carEntity = modelMapper.map(carRequestDTO, Car.class);
        Car savedCar = carRepository.save(carEntity);
        return modelMapper.map(savedCar, CarResponseDTO.class);
    }

    public Car updateCar(Long carId, CarRequestDTO carRequestDTO) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Couldn't find car with id: " + carId));

        modelMapper.map(carRequestDTO, car);
        return carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Couldn't find a student with id: " + carId));

        carRepository.delete(car);
    }
}