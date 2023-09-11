package rafael.rocha.msraces.domain.race.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.config.CarFeignClient;
import rafael.rocha.msraces.domain.race.model.Race;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RaceService {

    @Autowired
    private CarFeignClient carFeignClient;


    public Race createRace

    public List<Car> selectRandomCars() {
        List<Car> allCars = carFeignClient.getAllCars();

        int numberOfCarsToSelect = Math.min(new Random().nextInt(8) + 3, allCars.size());

        if (numberOfCarsToSelect <= 0) {
            return Collections.emptyList();
        }
        Collections.shuffle(allCars);
        return allCars.subList(0, numberOfCarsToSelect);
    }
}
