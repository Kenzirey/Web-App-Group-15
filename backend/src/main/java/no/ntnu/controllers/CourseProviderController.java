package no.ntnu.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.database.DatabaseManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for course provider endpoints.
 */
@RestController
public class CourseProviderController {
	private static final Logger LOGGER = Logger.getLogger(CourseProviderController.class.getName());

	/**
	 * Endpoint to get all available course providers.
	 * <p>Each product is represented as a map with string keys and values,
	 * detailing its properties.</p>
	 *
	 * @return A {@link ResponseEntity} object containing either:
	 *
	 *     <ul>
	 *         <li>A list of products (courses) with 200 OK if successful.</li>
	 *         <li>Or an empty response with 503 UNAVAILABLE if a database access error occurs.</li>
	 *     </ul>
	 */
	@GetMapping("/course-providers")
	public ResponseEntity<List<Map<String, String>>> getAllCourseProviders() {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			response = ResponseEntity.ok(DatabaseManager.getInstance().getAllCourseProviders());
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}

	/**
	 * Endpoint to search for course providers.
	 * <p>Returns a list of results that match the search criteria</p>
	 *
	 * @param query The query being used when searching for course providers.
	 *
	 * @return {@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A list of matching products with status 200 OK if
	 *         the operation is successful and matches are found.</li>
	 *         <li>An empty list with status 200 OK if no matches are found.</li>
	 *         <li>An empty response with status 503 SERVICE_UNAVAILABLE
	 *         if a database access error occurs.</li>
	 *     </ul>
	 */
	@GetMapping("/course-providers/{query}")
	//a list of maps, because we want key/value pairs. And key/value are both strings.
	public ResponseEntity<List<Map<String, String>>> searchCourseProvider(
			@PathVariable String query) {
		//ResponseEntity should match the return type of the method.
		ResponseEntity<List<Map<String, String>>> response;
		try {
			List<Map<String, String>> result
					= DatabaseManager.getInstance().searchCourseProvider(query);
			//variable = (condition) ? expressionTrue : expressionFalse;
			//If result is null, builds a response entity with an error code.
			//If not null, sends an OK response with the result.
			response = result == null
					? ResponseEntity.internalServerError().build() : ResponseEntity.ok(result);
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}
}
