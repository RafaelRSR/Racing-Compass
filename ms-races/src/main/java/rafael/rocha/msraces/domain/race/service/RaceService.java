package rafael.rocha.msraces.domain.race.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.config.CarFeignClient;
import rafael.rocha.msraces.domain.race.dto.RaceRequestDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResponseDTO;
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


    public RaceResponseDTO createRace(RaceRequestDTO raceRequestDTO) {
        List<Car> allCars = carFeignClient.getAllCars();

        List<Car> randomCars = selectRandomCars(allCars);

        Race race = new Race();
        race.setCarList(randomCars);

        race = raceRepository.save(race);

        RaceResponseDTO raceResponseDTO = new RaceResponseDTO();
        raceResponseDTO.setId(race.getId());

        return raceResponseDTO;
    }

    public void simulateRaceAndSendResult() {
        List<Car> selectedCars = selectRandomCars(carFeignClient.getAllCars());
        List<Car> raceResults = simulateRace(selectedCars);

        rabbitTemplate.convertAndSend("raceResultQueue", raceResults);
    }

    public List<Car> selectRandomCars(List<Car> allCars) {
        int numberOfCarsToSelect = Math.min(new Random().nextInt(8) + 3, allCars.size());

        if (numberOfCarsToSelect <= 0) {
            return Collections.emptyList();
        }
        Collections.shuffle(allCars);
        return allCars.subList(0, numberOfCarsToSelect);
    }

    private List<Car> simulateRace(List<Car> selectedCars) {
        List<Car> raceResults = new ArrayList<>(selectedCars);
        Collections.shuffle(raceResults);
        return raceResults;
    }
}
