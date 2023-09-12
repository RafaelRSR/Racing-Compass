package rafael.rocha.mshistory.domain.raceresult.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rafael.rocha.mshistory.domain.raceresult.document.RaceResultDocument;
import rafael.rocha.mshistory.domain.raceresult.repository.RaceResultRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RaceResultService {

    @Autowired
    private RaceResultRepository raceResultRepository;

    public List<RaceResultDocument> getAllRaceResults() {
        return raceResultRepository.findAll();
    }
    public Optional<RaceResultDocument> getRaceResultById(Long id) {
        return raceResultRepository.findById(id);
    }
}
