package rafael.rocha.mscars.domain.car.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.exceptions.CarNotFoundException;
import rafael.rocha.mscars.domain.car.exceptions.DuplicateCarException;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.mscars.domain.car.repository.CarRepository;
import rafael.rocha.mscars.domain.pilot.model.Pilot;
import rafael.rocha.mscars.domain.pilot.repository.PilotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PilotRepository pilotRepository;
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
        if (carRepository.existsByBrandAndModel(carRequestDTO.getBrand(), carRequestDTO.getModel())) {
            throw new DuplicateCarException("A car with the same brand and model already exists.");
        }

        Optional<Pilot> existingPilot = pilotRepository.findByName(carRequestDTO.getPilot().getName());
        if (!existingPilot.isPresent()) {
            Pilot newPilot = modelMapper.map(carRequestDTO.getPilot(), Pilot.class);
            existingPilot = Optional.of(pilotRepository.save(newPilot));
        }

        Car carEntity = modelMapper.map(carRequestDTO, Car.class);
        carEntity.setPilot(existingPilot.get());

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