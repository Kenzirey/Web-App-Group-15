package no.ntnu.database.services;

import java.util.Optional;
import no.ntnu.database.model.Category;
import no.ntnu.database.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic for category
 * Interacts with the {@link CategoryRepository} to perform CRUD operations.
 * Code adapted from app-dev repository by Gist.
 */
@Service
public class CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository repository;

	/**
	 * Creates the service class.
	 *
	 * @param categoryRepository The repository used for interacting with the database
	 */
	public CategoryService(@Autowired CategoryRepository categoryRepository) {
		this.repository = categoryRepository;
	}

	/**
	 * Adds a category in the database.
	 *
	 * @param category the category added in the database.
	 * @return the category id if the image has been inserted, if not return invalid
	 */
	public int add(Category category) {
		if (!category.isValid()) {
			LOGGER.warn("Category is invalid");
		}

		repository.save(category);
		return category.getCategoryId();
	}

	/**
	 * Returns all categories in the database.
	 *
	 * @return All the categories in the database
	 */
	public Iterable<Category> getAllCategories() {
		return repository.findAll();
	}

	/**
	 * Updates the category.
	 *
	 * @param category The new category
	 */
	public void update(int id, Category category) {
		Optional<Category> existingCategory = repository.findById(id);

		if (existingCategory.isEmpty()) {
			throw new IllegalStateException(String.format("No favorite: %s", id));
		} else {
			category.setCategoryId(id);
			repository.save(category);
		}

	}

	/**
	 *	Deletes a category from the database.
	 *
	 * @return Returns true if deleted. False if the doesn't exist int the database
	 */
	public boolean delete(int id) {
		Optional<Category> category = repository.findById(id);
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}
		if (category.isPresent()) {
			repository.deleteById(id);
		}
		return category.isPresent();
	}

	/**
	 * Searches for a specific category.
	 *
	 * @param query The search query to use when searching for categories.
	 *
	 * @return Any categories that match the search query
	 */
	public Iterable<Category> searchCategory(String query) {
		return repository.searchCategory(query);
	}



}
