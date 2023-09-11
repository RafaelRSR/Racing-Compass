package rafael.rocha.msraces.domain.race.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.car.dto.CarResponseDTO;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.domain.track.dto.TrackDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceResponseDTO {

    private Long id;
    private List<Car> cars;
    private TrackDTO track;
}
