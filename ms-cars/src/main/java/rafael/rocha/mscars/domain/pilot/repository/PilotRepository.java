package rafael.rocha.mscars.domain.pilot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafael.rocha.mscars.domain.pilot.model.Pilot;

import java.util.Optional;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    boolean existsByName(String name);

    Optional<Pilot> findByName(String name);
}
