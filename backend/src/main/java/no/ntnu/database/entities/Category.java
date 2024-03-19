package no.ntnu.database.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The class represents the category a course
 * mapped to a corresponding database table via JPA annotations.
 *
 */
@Entity
public class Category {

	@Id
	@Schema (description = "The unique id for the category", example = "1111")
	private int categoryId;

	@Schema (description = "The name of the category", example = "Java")
	private String categoryName;


	/**
	 * An empty constructor for JPA requirement.
	 */
	public Category() {
		// Empty constructor for JPA.
	}


	/**
	 * Returns the category id.
	 *
	 * @return the category id
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * Returns the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the category name of the category.
	 *
	 * @param categoryName The category name of the category.
	 */
	public void setCategoryName(String categoryName) {
		if (categoryName == null) {
			throw new IllegalArgumentException("The category name cannot be null");
		}
		this.categoryName = categoryName;
	}

	/**
	 * Sets the category id of the category.
	 *
	 * @param categoryId The category id of the category.
	 */
	public void setCategoryId(int categoryId) {
		if (categoryId < 0) {
			throw new IllegalArgumentException("The image ID cannot be less than 0");
		}
		this.categoryId = categoryId;
	}






}
