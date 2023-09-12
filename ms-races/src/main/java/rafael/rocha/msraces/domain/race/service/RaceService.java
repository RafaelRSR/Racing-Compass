package rafael.rocha.msraces.domain.race.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.config.CarFeignClient;
import rafael.rocha.msraces.domain.race.dto.RaceRequestDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResponseDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResultDTO;
import rafael.rocha.msraces.domain.race.model.Race;
import rafael.rocha.msraces.domain.race.repository.RaceRepository;

import java.util.*;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private CarFeignClient carFeignClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;


    public RaceResponseDTO createRace(RaceRequestDTO raceRequestDTO) {
        List<Car> allCars = carFeignClient.getAllCars();

        List<Car> randomCars = selectRandomCars(allCars);

        Race race = modelMapper.map(raceRequestDTO, Race.class);
        race.setCarList(randomCars);

        race = raceRepository.save(race);

        RaceResponseDTO raceResponseDTO = modelMapper.map(race, RaceResponseDTO.class);
        raceResponseDTO.setCars(randomCars);

        return raceResponseDTO;
    }

    public List<RaceResultDTO> simulateRaceById(Long raceId) {
        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with ID: " + raceId));

        List<Car> selectedCars = race.getCarList();
        List<RaceResultDTO> raceResults = simulateRace(selectedCars);

        rabbitTemplate.convertAndSend("raceResultQueue", raceResults);
        List<String> jsonRaceResults = raceResults.stream()
                .map(this::convertToJson)
                .toList();

        for (String jsonResult : jsonRaceResults) {
            rabbitTemplate.convertAndSend("raceResultQueue", jsonResult);
        }

        return raceResults;
    }

    public List<Car> selectRandomCars(List<Car> allCars) {
        int numberOfCarsToSelect = Math.min(new Random().nextInt(8) + 3, allCars.size());

        if (numberOfCarsToSelect <= 0) {
            return Collections.emptyList();
        }
        Collections.shuffle(allCars);
        return allCars.subList(0, numberOfCarsToSelect);
    }

    public List<RaceResultDTO> simulateRace(List<Car> selectedCars) {
        List<RaceResultDTO> raceResults = new ArrayList<>();
        List<Car> shuffledCars = new ArrayList<>(selectedCars);
        Collections.shuffle(shuffledCars);

        for (int i = 0; i < shuffledCars.size(); i++) {
            Car car = shuffledCars.get(i);
            RaceResultDTO raceResultDTO = modelMapper.map(car, RaceResultDTO.class);
            raceResultDTO.setPosition(i + 1);
            raceResults.add(raceResultDTO);
        }
        return raceResults;
    }

    private String convertToJson(RaceResultDTO raceResultDTO) {
        try {
            return objectMapper.writeValueAsString(raceResultDTO);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
