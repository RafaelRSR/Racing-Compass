package rafael.rocha.msraces.domain.race.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.car.dto.CarRequestDTO;
import rafael.rocha.msraces.domain.track.dto.TrackDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaceRequestDTO {

    @NotNull
    private List<CarRequestDTO> cars;

    @NotNull
    private TrackDTO track;
}
