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
 * Controller for product endpoints.
 */
@RestController
public class ProductController {
	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

	/**
	 * Endpoint to get all available products (courses).
	 * <p>Each product is represented as a map with string keys and values,
	 * detailing its properties.</p>
	 *
	 * @return A {@link ResponseEntity} object containing either:
	 *<li>A list of products (courses) with 200 OK if successful</li>
	 *<li>Or an empty response with 503 UNAVAILABLE if a database access error occurs. </li>
	 */
	@GetMapping("/products")
	public ResponseEntity<List<Map<String, String>>> getAllProducts() {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			response = ResponseEntity.ok(
					DatabaseManager.getInstance()
							.getAllProducts()
			);
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
	 *     <li>A list of matching products with status 200 OK,
	 *     if the operation is successful and matches are found.</li>
	 *     <li>An empty list with status 200 OK if no matches are found.</li>
	 *     <li>An empty response with status 503 SERVICE_UNAVAILABLE
	 *     if a database access error occurs.</li>
	 */
	@GetMapping("/products/{query}")
	public ResponseEntity<List<Map<String, String>>> searchProduct(@PathVariable String query) {
		ResponseEntity<List<Map<String, String>>> response;
		try {
			List<Map<String, String>> result = DatabaseManager.getInstance()
					.searchProduct(query);
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
