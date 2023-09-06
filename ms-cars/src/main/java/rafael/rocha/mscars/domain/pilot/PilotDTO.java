package rafael.rocha.mscars.domain.pilot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PilotDTO {
    private String name;
    private int age;
}
