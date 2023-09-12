package rafael.rocha.mshistory.race.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "races")
public class RaceDocument {
    @Id
    private Long id;
    private String raceName;
    private Date raceDate;
}
