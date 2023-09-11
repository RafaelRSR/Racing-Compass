package rafael.rocha.msraces.domain.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.msraces.domain.race.model.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
}
