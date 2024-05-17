package dk.ecc.bowlinghall;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class BowlingHallApplication {

	public static void main(String[] args) {
		SpringApplication.run(BowlingHallApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Copenhagen"));
	}

}
