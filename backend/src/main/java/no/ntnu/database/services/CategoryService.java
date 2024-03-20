package no.ntnu.database.services;

import no.ntnu.database.entities.Category;
import no.ntnu.database.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for handling business logic for category
 * Interacts with the {@link CategoryRepository} to perform CRUD operations.
 */
@Service
public class CategoryService {


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
	public String addCategory(Category category) {
		try {
			if (!repository.existsById(category.getCategoryId())) {
				repository.save(category);
				return "Category inserted:" + category.getCategoryName();
			} else {
				return "Category is invalid";
			}
		} catch (Exception e) {
			throw e;
		}
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
	public String updateCategory(Category category) {
		if (repository.existsById(category.getCategoryId())) {
			try {
				Category existingCategory = repository.findById(category.getCategoryId()).get();
				existingCategory.setCategoryId(category.getCategoryId());
				existingCategory.setCategoryName(category.getCategoryName());
				repository.save(existingCategory); // save the updated category
				return "Category updated";
			} catch (Exception e) {
				throw e;
			}
		} else {
			return "Category does not exists in the DB";
		}
	}

	/**
	 *	Deletes a category from the database.
	 *
	 * @return Returns true if deleted. False if the doesn't exist int the database
	 */
	public boolean deleteCategory(Category category) {
		if (repository.existsById(category.getCategoryId())) {
			try {
				repository.delete(category);
				return true;
			} catch (Exception e) {

				return false;
			}
		}
		return false;
	}

	/**
	 * Searches for a specific category.
	 *
	 * @param query The search query to use when searching for categories
	 * @return Any categories that match the search query
	 */
	public Iterable<Category> searchCategory(String query) {
		return repository.searchCategory(query);
	}



}
