package no.ntnu.database.controllers;

import no.ntnu.database.entities.Category;
import no.ntnu.database.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category endpoints.
 */
@CrossOrigin
@RestController
public class CategoryController {
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
}
