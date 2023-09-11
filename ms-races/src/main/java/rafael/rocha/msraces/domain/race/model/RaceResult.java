package rafael.rocha.msraces.domain.race.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.car.model.Car;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int position;

    @ManyToOne
    private Car car;
}
