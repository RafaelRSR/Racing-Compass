package rafael.rocha.mscars.domain.car.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rafael.rocha.mscars.domain.pilot.model.Pilot;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String brand;
    private String model;
    private Date year;
    @ManyToOne
    private Pilot pilot;

}
