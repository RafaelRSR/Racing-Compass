package rafael.rocha.msraces.domain.race.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.msraces.domain.track.model.Track;
import rafael.rocha.mscars.domain.car.model.Car;

import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Car> carList;
    @ManyToOne
    private Track track;

}
