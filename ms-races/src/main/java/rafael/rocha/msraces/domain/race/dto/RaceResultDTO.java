package rafael.rocha.msraces.domain.race.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.car.model.Car;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceResultDTO {

    private Long id;

    private int position;

    private Car car;

}