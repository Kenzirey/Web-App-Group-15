package no.ntnu.database.service;

import java.util.Optional;
import no.ntnu.database.model.Image;
import no.ntnu.database.repository.ImageRepository;
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
	 */
	public void add(Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Image \"image\" cannot be null");
		}
		imageRepository.save(image);
	}

	/**
	 * Updates the image.
	 *
	 * @param image The new image with new bytes
	 * @return True if the image existed & was updated, false if not
	 */
	public boolean update(int id, Image image) {
		boolean exists = false;
		if (imageRepository.existsById(id)) {
			exists = true;
			image.setImageId(id);
			imageRepository.save(image);
		}
		return exists;
	}

	/**
	 * Deletes an image from the database.
	 *
	 * @return Returns true if deleted. False if the image doesn't exist in the database
	 */
	public boolean deleteImage(int id) {
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}
		boolean existed = imageRepository.existsById(id);
		imageRepository.deleteById(id);
		return existed;
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
