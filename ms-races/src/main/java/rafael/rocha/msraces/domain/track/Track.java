package rafael.rocha.msraces.domain.track;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    private String name;
    private String country;
    private Date date;
}