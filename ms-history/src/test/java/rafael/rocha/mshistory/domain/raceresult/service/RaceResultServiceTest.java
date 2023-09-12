package rafael.rocha.mshistory.domain.raceresult.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import rafael.rocha.mshistory.domain.raceresult.document.RaceResultDocument;
import rafael.rocha.mshistory.domain.raceresult.repository.RaceResultRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RaceResultServiceTest {

    @InjectMocks
    private RaceResultService raceResultService;

    @Mock
    private RaceResultRepository raceResultRepository;

    @Test
    void getAllRaceResults() {
        List<RaceResultDocument> raceResults = new ArrayList<>();
        raceResults.add(new RaceResultDocument("1L", 1, "Car 1", "Model 1", "Pilot 1", new Date()));
        raceResults.add(new RaceResultDocument("2L", 2, "Car 2", "Model 2", "Pilot 2", new Date()));
        raceResults.add(new RaceResultDocument("3L", 3, "Car 3", "Model 3", "Pilot 3", new Date()));

        when(raceResultRepository.findAll()).thenReturn(raceResults);

        List<RaceResultDocument> result = raceResultService.getAllRaceResults();

        assertEquals(raceResults, result);

        verify(raceResultRepository, times(1)).findAll();
    }

    @Test
    void getRaceResultById() {
        Long raceResultId = 1L;
        RaceResultDocument raceResult = new RaceResultDocument("1L", 1, "Car 1", "Model 1", "Pilot 1", new Date());

        when(raceResultRepository.findById(raceResultId)).thenReturn(Optional.of(raceResult));

        Optional<RaceResultDocument> result = raceResultService.getRaceResultById(raceResultId);

        assertEquals(Optional.of(raceResult), result);

        verify(raceResultRepository, times(1)).findById(raceResultId);
    }
}