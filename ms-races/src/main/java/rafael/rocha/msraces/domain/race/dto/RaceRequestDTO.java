package rafael.rocha.msraces.domain.race.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.domain.track.model.Track;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaceRequestDTO {

    @NotNull
    private List<Car> cars;

    @NotNull
    private Track track;
}
