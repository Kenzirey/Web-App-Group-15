package no.ntnu.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entrypoint class for the draft course application.
 */
@SpringBootApplication
public class CourseApplication {

	/**
	 * Main method.
	 *
	 * @param args Program arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}
}
