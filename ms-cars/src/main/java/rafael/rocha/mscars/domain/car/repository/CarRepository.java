package rafael.rocha.mscars.domain.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rafael.rocha.mscars.domain.car.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByBrandAndModel(String brand, String model);
}
