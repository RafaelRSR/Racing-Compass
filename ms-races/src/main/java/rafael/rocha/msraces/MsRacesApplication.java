package rafael.rocha.msraces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "rafael.rocha.msraces.config")
@EntityScan({"rafael.rocha.msraces.domain", "rafael.rocha.msraces.domain.race.model"})
public class MsRacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRacesApplication.class, args);
	}

}
