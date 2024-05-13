package no.ntnu.database.services;

import java.util.Optional;
import no.ntnu.database.model.Image;
import no.ntnu.database.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for handling business logic for images
 * Interacts with the {@link ImageRepository} to perform CRUD operations.
 */
@Service
public class ImageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);


	private final ImageRepository imageRepository;


	/**
	 * Makes the image service.
	 *
	 * @param imageRepository The repository class for communication.
	 */
	@Autowired
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}


	/**
	 * Adds an image in the database.
	 *
	 * @param image the image added in the database.
	 * @return the image ID if the image has been inserted, if not return invalid
	 */
	public int add(Image image) {
		if (!image.isValid()) {
			LOGGER.warn("Favorite is invalid");
		}

		imageRepository.save(image);
		return image.getImageId();
	}

	/**
	 * Returns all images in the database.
	 *
	 * @return All the images in the database
	 */
	public Iterable<Image> getAllImages() {
		return imageRepository.findAll();
	}

	/**
	 * Updates the image.
	 *
	 * @param image The new image with a new url
	 */
	public void update(int id, Image image) {
		Optional<Image> existingImage = imageRepository.findById(id);
		if (existingImage.isEmpty()) {
			throw new IllegalStateException(String.format("No favorite: %s", id));
		} else {
			image.setImageId(id);
			imageRepository.save(image);
		}

	}


	/**
	 * Deletes an image from the database.
	 *
	 * @return Returns true if deleted. False if the image doesn't exist in the database
	 */
	public boolean deleteImage(int id) {
		Optional<Image> image = imageRepository.findById(id);
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}
		if (image.isPresent()) {
			imageRepository.deleteById(id);
		}
		return image.isPresent();
	}


	/**
	 * Returns an image from the database corresponding by the image ID.
	 *
	 * @param id The id of the image to return.
	 * @return The image, or an empty Optional if not found
	 */
	public Optional<Image> findById(int id) {
		return imageRepository.findById(id);
	}


}
