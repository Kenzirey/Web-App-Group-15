package no.ntnu.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.service.InfoRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for product endpoints.
 */
@RestController
public class CourseController {
	private static final Logger LOGGER = Logger.getLogger(CourseController.class.getName());

	private final InfoRequests requests;

	/**
	 * Creates the controller.
	 *
	 * @param infoRequests Autowired object for sending requests to the database
	 */
	@Autowired
	public CourseController(InfoRequests infoRequests) {
		this.requests = infoRequests;
	}

	/**
	 * Endpoint to get all available products (courses).
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
	@GetMapping("/courses")
	@Operation(
			summary = "Get all courses",
			description = "Get all courses available in the database."
			+ " Or 503 UNAVAILABLE if a database access error occurs."
	)
	public ResponseEntity<List<Map<String, String>>> getAllCourses() {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			response = ResponseEntity.ok(requests.getAllCourses());
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}

	/**
	 * Endpoint to search for products (courses).
	 * <p>Returns a list of results that match the search criteria.</p>
	 *
	 * @param query The query to use when searching for products.
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
	@GetMapping("/courses/{query}")
	@Operation(
			summary = "Get one course",
			description = "Search for one course that match the given query."
			+ " or 503 UNAVAILABLE if a database access error occurs."
	)
	public ResponseEntity<List<Map<String, String>>> searchCourse(
			@Parameter(description = "The query to use when searching for a specific course")
			@PathVariable String query) {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			List<Map<String, String>> result = requests.searchCourse(query);
			response = result == null
					? ResponseEntity.internalServerError().build()
					: ResponseEntity.ok(result);
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}
}
