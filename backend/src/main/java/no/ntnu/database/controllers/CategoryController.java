package no.ntnu.database.controllers;

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
 */
@CrossOrigin
@RestController
@RequestMapping("/category")
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
	@GetMapping(value = "/categories", produces = {"application/json"})
	public Iterable<Category> getAllCategories() {
		return service.getAllCategories();
	}

	/**
	 * Endpoint to search for categories.
	 *
	 * @param query The query to use when searching for categories
	 * @return Categories that match the search queries
	 */
	@GetMapping(value = "/categories/{query}", produces = {"application/json"})
	public Iterable<Category> searchCategory(@PathVariable String query) {
		return query == null || query.isBlank()
				? service.getAllCategories()
				: service.searchCategory(query);
	}

	/**
	 * Adds a category to the collection.
	 *
	 * @param category The category added
	 * @return 201 CREATED status on success, 400 Bad request on error
	 */
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
	 * Removes a category from the collection.
	 *
	 * @param id The id of the category to be removed.
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
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
	 * Updates a category in the repository.
	 *
	 * @param id The id of the category to update.
	 * @param category New category data to store.
	 * @return 200 OK status on success, 400 Bad request on error
	 */
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
