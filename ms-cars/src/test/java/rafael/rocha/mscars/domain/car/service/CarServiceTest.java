package rafael.rocha.mscars.domain.car.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.exceptions.CarNotFoundException;
import rafael.rocha.mscars.domain.car.exceptions.DuplicateCarException;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.mscars.domain.car.repository.CarRepository;
import rafael.rocha.mscars.domain.pilot.model.Pilot;
import rafael.rocha.mscars.domain.pilot.repository.PilotRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private PilotRepository pilotRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        cars.add(new Car());

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(carRepository).findAll();
    }

    @Test
    void getCarById() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = carService.getCarById(carId);

        assertNotNull(result);
        assertEquals(carId, result.getId());

        verify(carRepository).findById(carId);
    }

    @Test
    void getCarByIdCarNotFoundException() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.getCarById(carId));
        assertEquals("Car not found with id: " + carId, exception.getMessage());

        verify(carRepository).findById(carId);
    }

    @Test
    void createCarSuccessful() {
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Chevrolet");
        carRequestDTO.setModel("Equinox");

        Pilot pilot = new Pilot();
        pilot.setName("Felipe Massa");
        carRequestDTO.setPilot(pilot);

        Car carEntity = new Car();
        carEntity.setBrand(carRequestDTO.getBrand());
        carEntity.setModel(carRequestDTO.getModel());
        carEntity.setPilot(pilot);

        when(modelMapper.map(carRequestDTO, Car.class)).thenReturn(carEntity);
        when(carRepository.existsByBrandAndModel(carRequestDTO.getBrand(), carRequestDTO.getModel())).thenReturn(false);
        when(pilotRepository.findByName(pilot.getName())).thenReturn(Optional.of(pilot));
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        CarResponseDTO carResponseDTO = carService.createCar(carRequestDTO);

        assertNotNull(carResponseDTO);
        assertEquals(carEntity.getBrand(), carResponseDTO.getBrand());
        assertEquals(carEntity.getModel(), carResponseDTO.getModel());
        assertEquals(carEntity.getPilot().getName(), carResponseDTO.getPilot().getName());

        verify(modelMapper).map(carRequestDTO, Car.class);
        verify(carRepository).existsByBrandAndModel(carRequestDTO.getBrand(), carRequestDTO.getModel());
        verify(pilotRepository).findByName(pilot.getName());
        verify(carRepository).save(carEntity);
    }

    @Test
    void createCarDuplicateCarException() {
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Tesla");
        carRequestDTO.setModel("Model 3");

        when(carRepository.existsByBrandAndModel(carRequestDTO.getBrand(), carRequestDTO.getModel())).thenReturn(true);

        DuplicateCarException exception = assertThrows(DuplicateCarException.class, () -> carService.createCar(carRequestDTO));
        assertEquals("A car with the same brand and model already exists.", exception.getMessage());

        verify(carRepository).existsByBrandAndModel(carRequestDTO.getBrand(), carRequestDTO.getModel());
        verifyNoMoreInteractions(pilotRepository, modelMapper, carRepository);
    }

    @Test
    void updateCarSuccessful() {
        Long carId = 1L;
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Updated Brand");
        carRequestDTO.setModel("Updated Model");

        Car existingCar = new Car();
        existingCar.setId(carId);

        when(modelMapper.map(carRequestDTO, Car.class)).thenReturn(existingCar);
        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(existingCar)).thenReturn(existingCar);

        Car updatedCar = carService.updateCar(carId, carRequestDTO);

        assertEquals("Updated Brand", updatedCar.getBrand());
        assertEquals("Updated Model", updatedCar.getModel());

        Mockito.verify(carRepository).findById(carId);
        Mockito.verify(carRepository).save(existingCar);
    }

    @Test
    void updateCarCarNotFoundException() {
        Long carId = 1L;
        CarRequestDTO carRequestDTO = new CarRequestDTO();
        carRequestDTO.setBrand("Updated Brand");
        carRequestDTO.setModel("Updated Model");

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.updateCar(carId, carRequestDTO));
    }

    @Test
    void deleteCar() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> carService.deleteCar(carId));
        assertEquals("Couldn't find a student with id: " + carId, exception.getMessage());

        verify(carRepository).findById(carId);
        verifyNoMoreInteractions(carRepository);
    }
}