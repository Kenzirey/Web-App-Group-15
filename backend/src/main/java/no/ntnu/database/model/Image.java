package no.ntnu.database.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;


/**
 * The class represents an Image, with an id and link to the url
 * mapped to a corresponding database table via JPA annotations.
 */
@Entity
public final class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "An Unique ID for the image", example = "1")
	@Column(name = "image_id")
	private int imageId;

	@NotNull
	@JsonIgnore
	@Schema(description = "An URL pointing to the image associated with this entity", example = "https://www.ntnu.no/")
	@Column(name = "image_bytes", length = Integer.MAX_VALUE)
	private byte[] imageBytes;

	@NotNull
	@Schema(description = "The file type of the image", example = "jpeg")
	private String imageType;

	private String altText;

	@JsonIgnore
	@OneToOne(mappedBy = "image")
	private Course course;

	/**
	 * An empty constructor for JPA requirement.
	 */
	public Image() {}

	/**
	 * Creates an image.
	 *
	 * @param imageBytes The bytes that make up the image data
	 * @param imageType The filetype of the image
	 * @param altText The image's alt text / caption
	 */
	public Image(byte[] imageBytes, String imageType, String altText) {
		setImageBytes(imageBytes);
		setImageType(imageType);
		setAltText(altText);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
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
	 * Sets the imageBytes containing the image.
	 *
	 * @param imageBytes The imageBytes containing the image.
	 */
	public void setImageBytes(byte[] imageBytes) {
		if (imageBytes == null) {
			throw new IllegalArgumentException("The image url cannot be null");
		}
		this.imageBytes = imageBytes;
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
	 * Returns the imageBytes.
	 *
	 * @return the imageBytes containing the image.
	 */
	public byte[] getImageBytes() {
		return imageBytes;
	}


	/**
	 * Checks if the object is valid.
	 *
	 * @return True when valid, false when invalid
	 */
	@JsonIgnore
	public boolean isValid() {
		return imageBytes != null && imageBytes.length != 0;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}
}
