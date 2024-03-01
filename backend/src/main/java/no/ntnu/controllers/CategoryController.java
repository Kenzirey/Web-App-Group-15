package no.ntnu.controllers;

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
 * Controller for category endpoints.
 */
@RestController
public class CategoryController {
	private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

	private final InfoRequests requests;

	/**
	 * Creates the controller.
	 *
	 * @param infoRequests Autowired object for sending requests to the database
	 */
	@Autowired
	public CategoryController(InfoRequests infoRequests) {
		this.requests = infoRequests;
	}

	/**
	 * Endpoint to get all categories.
	 *
	 * @return All categories
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<Map<String, String>>> getAllCategories() {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			response = ResponseEntity.ok(requests.getAllCategories());
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}

	/**
	 * Endpoint to search for categories.
	 *
	 * @param query The query to use when searching for categories
	 * @return Categories that match the search queries
	 */
	@GetMapping("/categories/{query}")
	public ResponseEntity<List<Map<String, String>>> searchCategory(@PathVariable String query) {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			List<Map<String, String>> result = requests.searchCategory(query);
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
