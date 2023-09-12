package rafael.rocha.mshistory.raceresult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rafael.rocha.mshistory.raceresult.document.RaceResultDocument;
import rafael.rocha.mshistory.raceresult.service.RaceResultService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/race-results")
public class RaceResultController {

    @Autowired
    private RaceResultService raceResultService;

    @GetMapping
    public ResponseEntity<List<RaceResultDocument>> getAllRaceResults() {
        List<RaceResultDocument> raceResults = raceResultService.getAllRaceResults();
        return ResponseEntity.ok(raceResults);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getResultById(@PathVariable Long id) {
        Optional<RaceResultDocument> raceResult = raceResultService.getRaceResultById(id);
        return raceResult.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Race result not found!"));
    }
}
