package rafael.rocha.mscars.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRequestDTO {
    private String brand;
    private String model;
    private Date year;
    private Long pilotId;
}