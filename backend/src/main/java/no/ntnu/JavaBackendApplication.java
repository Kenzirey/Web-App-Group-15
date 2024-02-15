package no.ntnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main back-end spring boot application.
 */
@SpringBootApplication
public class JavaBackendApplication {
	/**
	 * Starts the application.
	 *
	 * @param args Program arguments. Ignored
	 */
	public static void main(String[] args) {
		SpringApplication.run(JavaBackendApplication.class, args);
	}

}
