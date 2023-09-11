package rafael.rocha.msraces.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import rafael.rocha.mscars.domain.car.model.Car;

import java.util.List;

@FeignClient(name = "ms-cars", url = "http://localhost:8080/v1/cars")
public interface CarFeignClient {
    @GetMapping
    List<Car> getAllCars();
}
