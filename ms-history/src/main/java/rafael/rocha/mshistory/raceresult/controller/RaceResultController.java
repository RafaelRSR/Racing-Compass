package rafael.rocha.mshistory.raceresult.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public ResponseEntity<RaceResultDocument> getRaceResultById(@PathVariable Long id) {
        Optional<RaceResultDocument> raceResult = raceResultService.getRaceResultById(id);
        return raceResult.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
