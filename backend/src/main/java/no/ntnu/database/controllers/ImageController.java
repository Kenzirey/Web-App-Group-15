package no.ntnu.database.controllers;

import java.util.Optional;
import no.ntnu.database.entities.Image;
import no.ntnu.database.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST API controller for Image collection.
 */
@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	private final ImageService imageService;


	/**
	 * Makes the image controller.
	 */
	public ImageController(@Autowired ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * Returns all images in the database.
	 *
	 * @return All images in the database.
	 */
	@GetMapping
	public Iterable<Image> getAllImages() {
		LOGGER.info("Getting all images");
		return imageService.getAllImages();
	}


	/**
	 * Endpoint to search for a specific image.
	 *
	 * @param id The id of the image to return.
	 * @return {@link ResponseEntity} object containing either:
	 * <ul>
	 *    <li>A corresponding image that matches the id, returns status 200</li>
	 *    <li>If no match is found, return status 404</li>
	 * </ul>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Image> getImage(@PathVariable Integer id) {
		ResponseEntity<Image> response;
		Optional<Image> image = imageService.findById(id);
		if (image.isPresent()) {
			response = ResponseEntity.ok(image.get());
		} else {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}


	/**
	 * Adds an image to the collection.
	 *
	 * @param image The image added
	 * @return 201 CREATED status on success, 400 Bad request on error
	 */
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Image image) {
		ResponseEntity<String> response;
		try {
			int id = imageService.add(image);
			response = new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;

	}


	/**
	 * Removes an image from the collection.
	 *
	 * @param id The id of image to be removed.
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		ResponseEntity<String> response;
		if (imageService.deleteImage(id)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	/**
	 * Update image in the repository.
	 *
	 * @param id    The id of the image to update.
	 * @param image New image data to store.
	 * @return 200 OK status on success, 400 Bad request on error
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody Image image) {
		ResponseEntity<String> response;
		try {
			imageService.update(id, image);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}


}
