package no.ntnu.database.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The class represents an Image, with an id and link to the url
 * mapped to a corresponding database table via JPA annotations.
 *
 */
@Entity
public final class Image {

	@Id
	@Schema(description = "An Unique ID for the image", example = "1111")
	private int imageId;

	@Schema(description = "The unique id for the course the image is used for", example = "1")
	private int courseId;

	@Schema(description = "An URL pointing to the image associated with this entity", example = "https://www.ntnu.no/")
	private String imageUrl;


	/**
	 * An empty constructor for JPA requirement.
	 */
	public Image() {
		// Empty constructor for JPA.
	}

	/**
	 * Sets the imageId of the image.
	 *
	 * @param imageId The imageId of the image.
	 */
	public void setImageId(int imageId) {
		if (imageId < 0) {
			throw new IllegalArgumentException("The image ID cannot be less than 0");
		}
		this.imageId = imageId;
	}

	/**
	 * Stes the courseId for the image.
	 *
	 * @param courseId The new course id.
	 */
	public void setCourseId(int courseId) {
		if (imageId < 0) {
			throw new IllegalArgumentException("The course ID cannot be less than 0");
		}
		this.courseId = courseId;
	}


	/**
	 * Sets the ImageUrl for the image.
	 *
	 * @param imageUrl The imageUrl of the image.
	 */
	public void setImageUrl(String imageUrl) {
		if (imageUrl == null) {
			throw new IllegalArgumentException("The image url cannot be null");
		}
		this.imageUrl = imageUrl;
	}

	/**
	 * Returns the imageId.
	 *
	 * @return The imageId for the image.
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * Returns the course id.
	 *
	 * @return the course id.
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * Returns the imageUrl.
	 *
	 * @return the imageUrl for the image.
	 */
	public String getImageUrl() {
		return imageUrl;
	}



}
