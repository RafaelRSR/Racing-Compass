package rafael.rocha.msraces.domain.track.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {

    private String name;
    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
