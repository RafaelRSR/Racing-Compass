package rafael.rocha.mshistory.race.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rafael.rocha.mshistory.race.document.RaceDocument;

public interface RaceRepository extends MongoRepository<RaceDocument, Long> {
}
