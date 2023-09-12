package rafael.rocha.msraces.domain.race.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.config.CarFeignClient;
import rafael.rocha.msraces.domain.race.dto.RaceRequestDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResponseDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResultDTO;
import rafael.rocha.msraces.domain.race.model.Race;
import rafael.rocha.msraces.domain.race.repository.RaceRepository;

import rafael.rocha.msraces.domain.track.dto.TrackDTO;
import rafael.rocha.msraces.domain.track.model.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RaceServiceTest {

    @InjectMocks
    private RaceService raceService;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private CarFeignClient carFeignClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Spy
    private ObjectMapper objectMapper;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRace() {
        CarRequestDTO carRequestDTO1 = new CarRequestDTO("Car 1", "Model 1", new Date(), null);
        CarRequestDTO carRequestDTO2 = new CarRequestDTO("Car 2", "Model 2", new Date(), null);
        CarRequestDTO carRequestDTO3 = new CarRequestDTO("Car 3", "Model 3", new Date(), null);

        TrackDTO trackDTO = new TrackDTO("Sample Track", "Sample Country", new Date());

        RaceRequestDTO raceRequestDTO = new RaceRequestDTO(
                Arrays.asList(carRequestDTO1, carRequestDTO2, carRequestDTO3),
                trackDTO
        );

        List<Car> mockCars = Arrays.asList(
                new Car(1L, "Car 1", "Model 1", new Date(), null),
                new Car(2L, "Car 2", "Model 2", new Date(), null),
                new Car(3L, "Car 3", "Model 3", new Date(), null)
        );

        Track mockTrack = new Track();
        mockTrack.setId(1L);
        mockTrack.setName("Sample Track");
        mockTrack.setCountry("Sample Country");
        mockTrack.setDate(new Date());

        when(carFeignClient.getAllCars()).thenReturn(mockCars);

        Race mockRace = new Race();
        mockRace.setId(1L);
        mockRace.setCarList(mockCars);
        mockRace.setTrack(mockTrack);

        when(modelMapper.map(raceRequestDTO, Race.class)).thenReturn(mockRace);
        when(raceRepository.save(mockRace)).thenReturn(mockRace);

        RaceResponseDTO responseDTO = raceService.createRace(raceRequestDTO);

        assertEquals(3, responseDTO.getCars().size());
        assertEquals("Sample Track", responseDTO.getTrack().getName());
        assertEquals("Sample Country", responseDTO.getTrack().getCountry());
    }

    @Test
    void simulateRaceById() {
        List<Car> allCars = createTestCars(20);

        List<Car> selectedCars = raceService.selectRandomCars(allCars);

        assertTrue(selectedCars.size() >= 3 && selectedCars.size() <= 10);
        assertTrue(allCars.containsAll(selectedCars));
    }

    @Test
    void selectRandomCars() {
        List<Car> allCars = createTestCars(20);

        List<Car> selectedCars = raceService.selectRandomCars(allCars);

        assertTrue(selectedCars.size() >= 3 && selectedCars.size() <= 10);
        assertTrue(allCars.containsAll(selectedCars));
    }

    @Test
    void testSelectRandomCarsWithFewerCarsAvailable() {
        List<Car> allCars = createTestCars(2);

        List<Car> selectedCars = raceService.selectRandomCars(allCars);

        assertEquals(2, selectedCars.size());
    }

    @Test
    void simulateRace() {
        Long raceId = 1L;
        Race race = new Race();
        race.setCarList(createTestCars(5));
        when(raceRepository.findById(raceId)).thenReturn(java.util.Optional.of(race));

        List<RaceResultDTO> raceResults = raceService.simulateRaceById(raceId);

        assertNotNull(raceResults);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("raceResultQueue"), any(List.class));
    }

    private List<Car> createTestCars(int count) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Car car = new Car();
            car.setId((long) i);
            car.setBrand("Brand " + i);
            cars.add(car);
        }
        return cars;
    }
}