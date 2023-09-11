package rafael.rocha.msraces.domain.race.dto;

import lombok.Data;
import rafael.rocha.mscars.domain.car.model.Car;

@Data
public class RaceResultDTO {

    private int position;

    private Car car;
}
