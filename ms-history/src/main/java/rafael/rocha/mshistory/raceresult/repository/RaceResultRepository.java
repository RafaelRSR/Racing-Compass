package rafael.rocha.mshistory.raceresult.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rafael.rocha.mshistory.raceresult.document.RaceResultDocument;

public interface RaceResultRepository extends MongoRepository<RaceResultDocument, Long> {
}
