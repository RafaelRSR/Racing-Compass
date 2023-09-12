package rafael.rocha.mshistory.domain.raceresult.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.mshistory.domain.raceresult.document.RaceResultDocument;

@Repository
public interface RaceResultRepository extends MongoRepository<RaceResultDocument, Long> {
}
