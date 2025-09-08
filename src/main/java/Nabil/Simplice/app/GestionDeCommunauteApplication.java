package Nabil.Simplice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication @EnableJpaAuditing
public class GestionDeCommunauteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDeCommunauteApplication.class, args);
	}

}
