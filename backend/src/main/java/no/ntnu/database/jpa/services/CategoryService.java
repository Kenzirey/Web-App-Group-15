package no.ntnu.database.jpa.services;

import no.ntnu.database.jpa.Category;
import no.ntnu.database.jpa.Image;
import no.ntnu.database.jpa.repositories.CategoryRepository;
import no.ntnu.database.jpa.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for handling business logic for category
 * Interacts with the {@link ImageRepository} to perform CRUD operations.
 */
@Service
public class CategoryService {


	@Autowired
	private CategoryRepository categoryRepository;


	/**
	 * Adds a category in the database.
	 *
	 * @param category the category added in the database.
	 * @return the category id if the image has been inserted, if not return invalid
	 */
	public String addCategory(Category category) {
		try {
			if (!categoryRepository.existsById(category.getCategoryId())) {
				categoryRepository.save(category);
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
		return categoryRepository.findAll();
	}

	/**
	 * Updates the category.
	 *
	 * @param category The new category
	 */
	public String updateCategory(Category category) {
		if (categoryRepository.existsById(category.getCategoryId())) {
			try {
				Category existingCategory = categoryRepository.findById(category.getCategoryId()).get();
				existingCategory.setCategoryId(category.getCategoryId());
				existingCategory.setCategoryName(category.getCategoryName());
				categoryRepository.save(existingCategory); // save the updated category
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
		if (categoryRepository.existsById(category.getCategoryId())) {
			try {
				categoryRepository.delete(category);
				return true;
			} catch (Exception e) {

				return false;
			}
		}
		return false;
	}





}
