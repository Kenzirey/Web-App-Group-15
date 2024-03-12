package no.ntnu.controllers;

import no.ntnu.database.jpa.Category;
import no.ntnu.database.jpa.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category endpoints.
 */
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
	@GetMapping("/categories")
	public Iterable<Category> getAllCategories() {
		return service.getAllCategories();
	}

	/**
	 * Endpoint to search for categories.
	 *
	 * @param query The query to use when searching for categories
	 * @return Categories that match the search queries
	 */
	@GetMapping("/categories/{query}")
	public Iterable<Category> searchCategory(@PathVariable String query) {
		return query == null || query.isBlank()
				? service.getAllCategories()
				: service.searchCategory(query);
	}
}
