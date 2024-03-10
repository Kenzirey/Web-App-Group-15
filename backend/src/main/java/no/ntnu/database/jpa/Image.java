package no.ntnu.database.jpa;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The class represents an Image, with an id and link to the url.
 *
 */
@Entity
public final class Image {

	@Id
	@Schema(description = "An Unique ID for the image", example = "1111")
	private int imageId;

	@Schema(description = "An URL pointing to the image associated with this entity")
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
		this.imageId = imageId;
	}

	/**
	 * Sets the ImageUrl for the image.
	 *
	 * @param imageUrl The imageUrl of the image.
	 */
	public void setImageUrl(String imageUrl) {
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
	 * Returns the imageUrl.
	 *
	 * @return the imageUrl for the image.
	 */
	public String getImageUrl() {
		return imageUrl;
	}



}
