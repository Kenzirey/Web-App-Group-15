package no.ntnu.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.database.DatabaseManager;
import no.ntnu.database.ResultFormatUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category endpoints.
 */
@RestController
public class CategoryController {
	private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

	/**
	 * Endpoint to get all categories.
	 *
	 * @return All categories
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<Map<String, String>>> getAllCategories() {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			response = ResponseEntity.ok(
					DatabaseManager.getInstance()
							.getAllCategories(ResultFormatUtil::formatResultAs2dArray)
			);
		} catch (SQLException sqle) {
			response = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
			LOGGER.log(Level.WARNING, "An SQLException occurred", sqle);
		}
		return response;
	}
}
