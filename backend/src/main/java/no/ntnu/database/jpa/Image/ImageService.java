package no.ntnu.database.jpa.Image;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

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
	public List<Image> getAllImages() {
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
	 * @return Returns true if deleted. False if the image doesn't exist image
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
