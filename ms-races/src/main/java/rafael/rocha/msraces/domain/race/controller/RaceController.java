package rafael.rocha.msraces.domain.race.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rafael.rocha.msraces.domain.race.dto.RaceRequestDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResponseDTO;
import rafael.rocha.msraces.domain.race.dto.RaceResultDTO;
import rafael.rocha.msraces.domain.race.service.RaceService;

import java.util.List;

@RestController
@RequestMapping("/v1/race")
public class RaceController {

    @Autowired
    private RaceService raceService;

    @PostMapping("/create")
    public ResponseEntity<RaceResponseDTO> createRace(@RequestBody RaceRequestDTO raceRequestDTO) {
        RaceResponseDTO raceResponseDTO = raceService.createRace(raceRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(raceResponseDTO);
    }

    @PostMapping("/simulate/{raceId}")
    public ResponseEntity<List<RaceResultDTO>> simulateRaceAndSendResult(@PathVariable Long raceId) {
        List<RaceResultDTO> raceResults = raceService.simulateRaceById(raceId);
        return new ResponseEntity<>(raceResults, HttpStatus.OK);
    }
}
