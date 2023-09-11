package rafael.rocha.msraces.domain.race.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rafael.rocha.mscars.domain.car.model.Car;
import rafael.rocha.msraces.domain.race.dto.RaceRequestDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResponseDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResultDTO;
import rafael.rocha.msraces.domain.race.service.RaceService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RaceController.class)
class RaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RaceService raceService;

    @Test
    void createRaceSuccessful() throws Exception{
        RaceRequestDTO requestDTO = new RaceRequestDTO();
        RaceResponseDTO responseDTO = new RaceResponseDTO();
        responseDTO.setId(1L);

        List<Car> mockCars = Arrays.asList(
                new Car(1L, "Car 1", "Model 1", new Date(), null),
                new Car(2L, "Car 2", "Model 2", new Date(), null)
        );

        responseDTO.setCars(mockCars);

        when(raceService.createRace(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/race/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cars", hasSize(2)))
                .andExpect(jsonPath("$.cars[0].brand").value("Car 1"))
                .andExpect(jsonPath("$.cars[1].brand").value("Car 2"));
    }

    @Test
    void simulateRaceAndSendResult() throws Exception{
        List<RaceResultDTO> expectedResults = Arrays.asList(
                new RaceResultDTO(1L,2, new Car(1L, "Car 1", "Model 1", new Date(), null)),
                new RaceResultDTO(2L,3, new Car(2L, "Car 2", "Model 2", new Date(), null))
        );

        Mockito.when(raceService.simulateRaceById(Mockito.anyLong())).thenReturn(expectedResults);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/race/simulate/{raceId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].car.brand").value("Car 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].position").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].car.brand").value("Car 2"));

        Mockito.verify(raceService, Mockito.times(1)).simulateRaceById(1L);
    }
}
