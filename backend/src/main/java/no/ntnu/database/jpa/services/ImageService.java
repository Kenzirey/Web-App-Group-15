package no.ntnu.database.jpa.services;


import no.ntnu.database.jpa.Image;
import no.ntnu.database.jpa.repositories.CourseRepository;
import no.ntnu.database.jpa.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic for images
 * Interacts with the {@link ImageRepository} to perform CRUD operations.
 */
@Service
public class ImageService {

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
	public boolean deleteImage(Image image) {
		if (imageRepository.existsById(image.getImageId())) {
			try {
				imageRepository.delete(image);
				return true;
			} catch (Exception e) {

				return false;
			}
		}
		return false;
	}













}
