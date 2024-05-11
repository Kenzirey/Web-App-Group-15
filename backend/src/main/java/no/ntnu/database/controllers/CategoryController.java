package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.database.entities.Category;
import no.ntnu.database.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category endpoints.
 * Code adapted from app-dev repository by Gist.
 */
@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	private final CategoryService service;

	/**
	 * Creates the controller.
	 *
	 * @param categoryService Autowired object for sending requests to the database
	 */
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.service = categoryService;
	}

	/**
	 * Endpoint to get all categories.
	 *
	 * @return All categories
	 */
	@Operation(summary = "Get all categories",
			description = "Returns a list of all the categories in the database")
	@ApiResponse(responseCode = "200", description = "successful request")
	@GetMapping(produces = {"application/json"})
	public Iterable<Category> getAllCategories() {
		LOGGER.info("Getting all categories");
		return service.getAllCategories();
	}

	/**
	 * Endpoint to search for categories.
	 *
	 * @param query The query to use when searching for categories.
	 *
	 * @return Categories that match the search queries
	 */
	@Operation(summary = "Search for categories",
			description = "Search for categories based on a database query string")
	//TODO: Improve swagger here with api responses.
	@ApiResponse(responseCode = "200", description = "Successfully searched")
	@GetMapping(value = "{query}", produces = {"application/json"})
	public Iterable<Category> searchCategory(@PathVariable String query) {
		return query == null || query.isBlank()
				? service.getAllCategories()
				: service.searchCategory(query);
	}

	/**
	 * Adds a new {@link Category} to the collection.
	 *
	 * @param category The {@link Category} added.
	 *
	 * @return a {@link ResponseEntity} with 201 CREATED status on success,
	 * 									400 Bad request on error
	 * 									or 403 if incorrect authorization.
	 */
	@Operation(summary = "Adds a new category",
			description = "Adds a new category to the database")
	@ApiResponse(responseCode = "201", description = "Category successfully added")
	@ApiResponse(responseCode = "400", description = "Bad request, category not added")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Category category) {
		ResponseEntity<String> response;
		try {
			int id = service.add(category);
			response = new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;

	}


	/**
	 * Removes a {@link Category} from the collection.
	 *
	 * @param id The id of the {@link Category} to be removed.
	 *
	 * @return a {@link ResponseEntity} with 200 OK status on success,
	 * 									403 if incorrect authorization
	 * 									or 404 NOT FOUND on error.
	 */
	@Operation(summary = "Deletes a category",
			description = "Deletes a category from the database, based on its provided id")
	@ApiResponse(responseCode = "200", description = "Category successfully deleted")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@ApiResponse(responseCode = "404", description = "Category not found")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> response;
		if (service.delete(id)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return  response;
	}


	/**
	 * Updates a {@link Category} in the database.
	 *
	 * @param id The id of the {@link Category} to update.
	 * @param category the {@link Category} being added to the database.
	 *
	 * @return a {@link ResponseEntity} with code 200 on success,
	 * 									400 if it is a bad request
	 * 									or 403 if not authorized.
	 */
	@Operation(summary = "Updates a category",
			description = "Updates an existing category in the database, "
					+ "based on its provided id")
	@ApiResponse(responseCode = "200", description = "Category successfully updated")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody Category category) {
		ResponseEntity<String> response;
		try {
			service.update(id, category);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}

}
