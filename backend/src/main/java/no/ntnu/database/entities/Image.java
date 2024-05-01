package no.ntnu.database.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


/**
 * The class represents an Image, with an id and link to the url
 * mapped to a corresponding database table via JPA annotations.
 *
 */
@Entity
public final class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "An Unique ID for the image", example = "1")
	@Column(name = "image_id")
	private int imageId;
	@Schema(description = "An URL pointing to the image associated with this entity", example = "https://www.ntnu.no/")
	@Column(name = "image_url")
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;


	/**
	 * An empty constructor for JPA requirement.
	 */
	public Image() {
		// Empty constructor for JPA.
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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
	 * Returns the imageUrl.
	 *
	 * @return the imageUrl for the image.
	 */
	public String getImageUrl() {
		return imageUrl;
	}


	/**
	 * Checks if the object is valid.
	 *
	 * @return True when valid, false when invalid
	 */
	@JsonIgnore
	public boolean isValid() {
		return imageUrl != null && !imageUrl.isBlank();
	}

}
