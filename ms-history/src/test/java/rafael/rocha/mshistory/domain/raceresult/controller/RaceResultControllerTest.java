package rafael.rocha.mshistory.domain.raceresult.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rafael.rocha.mshistory.domain.raceresult.document.RaceResultDocument;
import rafael.rocha.mshistory.domain.raceresult.service.RaceResultService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RaceResultController.class)
class RaceResultControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RaceResultService raceResultService;

    private RaceResultDocument testRaceResult;

    @BeforeEach
    void setUp() {
        testRaceResult = new RaceResultDocument("1L", 1, "Car 1", "Model 1", "Pilot 1", new Date());
    }

    @Test
    void getAllRaceResults() throws Exception {
        List<RaceResultDocument> expectedRaceResults = new ArrayList<>();

        when(raceResultService.getAllRaceResults()).thenReturn(expectedRaceResults);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/race-results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(expectedRaceResults.size()));
    }

    @Test
    void getResultById() throws Exception {
        when(raceResultService.getRaceResultById(1L)).thenReturn(Optional.of(testRaceResult));

        ResultActions result = mockMvc.perform(get("/v1/race-results/get/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1L"))
                .andExpect(jsonPath("$.position").value(1))
                .andExpect(jsonPath("$.carBrand").value("Car 1"))
                .andExpect(jsonPath("$.carModel").value("Model 1"))
                .andExpect(jsonPath("$.pilotName").value("Pilot 1"));

        Mockito.verify(raceResultService, times(1)).getRaceResultById(1L);
    }

    @Test
    void getResultById_NonExistingId() throws Exception {

        when(raceResultService.getRaceResultById(2L)).thenReturn(Optional.empty());

        ResultActions result = mockMvc.perform(get("/v1/race-results/get/2")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound())
                .andExpect(content().string("Race result not found!"));

        Mockito.verify(raceResultService, times(1)).getRaceResultById(2L);
    }
}