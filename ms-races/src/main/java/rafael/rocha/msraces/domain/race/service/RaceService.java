package rafael.rocha.msraces.domain.race.service;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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


    public RaceResponseDTO createRace(RaceRequestDTO raceRequestDTO) {
        List<Car> allCars = carFeignClient.getAllCars();

        List<Car> randomCars = selectRandomCars(allCars);

        Race race = modelMapper.map(raceRequestDTO, Race.class);
        race.setCarList(randomCars);

        race = raceRepository.save(race); //

        return modelMapper.map(race, RaceResponseDTO.class);
    }

    public List<RaceResultDTO> simulateRaceById(Long raceId) {
        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new EntityNotFoundException("Race not found with ID: " + raceId));

        List<Car> selectedCars = race.getCarList();
        List<RaceResultDTO> raceResults = simulateRace(selectedCars);

        rabbitTemplate.convertAndSend("raceResultQueue", raceResults);

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

    private List<RaceResultDTO> simulateRace(List<Car> selectedCars) {
        List<RaceResultDTO> raceResults = new ArrayList<>();
        List<Car> shuffledCars = new ArrayList<>(selectedCars);
        Collections.shuffle(shuffledCars);

        for (int i = 0; i < shuffledCars.size(); i++) {
            Car car = shuffledCars.get(i);
            RaceResultDTO raceResultDTO = new RaceResultDTO();
            raceResultDTO.setCar(car);
            raceResultDTO.setPosition(i + 1);
            raceResults.add(raceResultDTO);
        }
        return raceResults;
    }

}
