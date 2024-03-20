package no.ntnu.database.services;


import no.ntnu.database.entities.Image;
import no.ntnu.database.repositories.ImageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling business logic for images
 * Interacts with the {@link ImageRepository} to perform CRUD operations.
 */
@Service
public class ImageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	private ImageRepository imageRepository;


	/**
	 * Adds an image in the database.
	 *
	 * @param image the image added in the database.
	 * @return the image ID if the image has been inserted, if not return invalid
	 */
	public String addImage(Image image) {
		try {
			if (!imageRepository.existsById(image.getImageId())) {
				imageRepository.save(image);
				return "Image inserted:" + image.getImageId();
			} else {
				return "Image is invalid";
			}
		} catch (Exception e) {
			throw e;
		}
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
	public String updateImage(Image image) {
		if (imageRepository.existsById(image.getImageId())) {
			try {
			Image existingImage = imageRepository.findById(image.getImageId()).get();
			existingImage.setImageUrl(image.getImageUrl());
			imageRepository.save(existingImage);
			return "Image updated";
		} catch (Exception e) {
				throw e;
			}
		} else {
			return "Image does not exists in the DB";
		}
	}


	/**
	 *	Deletes an image from the database.
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
