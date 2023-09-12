package rafael.rocha.mshistory.domain.raceresult.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "race_result")
public class RaceResultDocument {
    @Id
    private String id;

    private int position;

    private String carBrand;

    private String carModel;

    private String pilotName;

    private Date raceDate;
}
