package no.ntnu.database.controllers;


import no.ntnu.database.entities.Image;
import no.ntnu.database.services.ImageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * REST API controller for Image colelction
 *
 */
@CrossOrigin
@RestController
public class ImageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	private final ImageService imageService;


	/**
	 * Makes the image controller.
	 *
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
	 * @param id 	The id of the image to return.
	 * @return 		{@link ResponseEntity} object containing either:
	 * 		<ul>
	 * 		  <li>A corresponding image that matches the id, returns status 200</li>
	 * 		  <li>If no match is found, return status 404</li>
	 * 		</ul>
	 */
	@GetMapping("/{Id}")
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
	 * Adds an image to the collection
	 * TODO:Fix recursion
	 *
	 * @param image The image added
	 * @return 201 CREATED status on success, 400 Bad request on error
	 */
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Image image) {
		ResponseEntity<String> response;
		try {
			add(image);
			response = new ResponseEntity<>(HttpStatus.CREATED);
		}  catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
 	}


	/**
	 * Removes an image from the collection
	 *
	 * @param id The id of image to be removed.
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
	@DeleteMapping
	public ResponseEntity<String> remove(@PathVariable int id) {
		ResponseEntity<String> response;
		if (imageService.deleteImage(id)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return  response;
	}

	/**
	 * Update image in the repository
	 *
	 * @param id The id of the image to update.
	 * @param image New image data to store.
	 * @return 200 OK status on success, 400 Bad request on error
	 */
	@PutMapping
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody Image image) {
		ResponseEntity<String> response;
		try {
			update(id, image);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}


}
