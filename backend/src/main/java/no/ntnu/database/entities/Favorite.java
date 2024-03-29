package no.ntnu.database.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * This class represents the users favorite,
 * with the userID and productID.
 *
 */
@Entity
public final class Favorite {


	@Id
	@Schema(description = "An Unique ID for the user", example = "1111")
	private int userId;


	@Schema(description = "An Unique ID for the product whom the user has marked favorite", example = "1111")
	private int productId;

	/**
	 * An empty constructor for JPA requirement.
	 */
	public Favorite() {
		// Empty constructor for JPA.
	}


	/**
	 * Sets the userId for favourite.
	 *
	 * @param userId The new userId.
	 */
	public void setUserId(int userId) {
		if (userId < 0) {
			throw new IllegalArgumentException("The userID ID cannot be less than 0");
		}
	}


	/**
	 * Sets the productId for favourite.
	 *
	 * @param productId The new productId.
	 */
	public void setProductId(int productId) {
		if (productId < 0) {
			throw new IllegalArgumentException("The product ID cannot be less than 0");
		}
		this.productId = productId;
	}

	/**
	 * Returns the userId for favourite.
	 *
	 * @return The userId for the favourite.
	 */
	public int getUserId() {
		return userId;
	}


	/**
	 * Returns the productId for favourite.
	 *
	 * @return The productId for the favourite.
	 */
	public int getProductId() {
		return productId;
	}

}
