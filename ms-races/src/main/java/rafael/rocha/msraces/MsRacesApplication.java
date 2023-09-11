package rafael.rocha.msraces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "rafael.rocha.msraces.config")
public class MsRacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRacesApplication.class, args);
	}

}
