package no.ntnu.database.controller;

import java.io.IOException;
import java.util.function.Function;
import no.ntnu.database.model.Image;
import no.ntnu.database.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * REST API controller for Image collection.
 */
@CrossOrigin
@RestController
@RequestMapping("/images")
public class ImageController {
	/**
	 * 1MB is spring's default max file size, if not otherwise specified.
	 */
	@Value("${spring.servlet.multipart.max-file-size:1000000}")
	private int sizeLimit;

	private final ImageService imageService;

	/**
	 * Makes the image controller.
	 */
	public ImageController(@Autowired ImageService imageService) {
		this.imageService = imageService;
	}

	private <T> ResponseEntity<T> getImageResponse(
			MultipartFile imgFile,
			String altText,
			Function<Image, ResponseEntity<T>> ifFileIsImage
	) {
		Image image = null;
		HttpStatus errorStatus = null;
		String contentType = imgFile.getContentType();
		if (contentType == null || !contentType.startsWith("image")) {
			errorStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (imgFile.getSize() > (sizeLimit == -1 ? Integer.MAX_VALUE : sizeLimit)) {
			errorStatus = HttpStatus.PAYLOAD_TOO_LARGE;
		} else {
			try {
				image = new Image(imgFile.getBytes(), contentType.split("/")[1], altText);
			} catch (IOException ioe) {
				errorStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			}
		}

		ResponseEntity<T> response;
		if (image != null) {
			response = ifFileIsImage.apply(image);
		} else {
			response = new ResponseEntity<>(errorStatus);
		}
		return response;
	}

	/**
	 * Endpoint to search for a specific image.
	 *
	 * @param id The id of the image to return.
	 * @return {@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A corresponding image that matches id, returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
		ResponseEntity<byte[]> response;
		response = imageService
				.findById(id)
				.map(image -> {
					MediaType mediaType = switch (image.getImageType()) {
						case "gif" -> MediaType.IMAGE_GIF;
						case "png" -> MediaType.IMAGE_PNG;
						default -> MediaType.IMAGE_JPEG;
					};
					return ResponseEntity.ok().contentType(mediaType).body(image.getImageBytes());
				})
				.orElse(ResponseEntity.notFound().build());
		return response;
	}


	/**
	 * Adds an image to the collection.
	 *
	 * @param imgFile The image added
	 * @return 201 CREATED status on success, 400 Bad request on error
	 */
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Integer> add(
			@RequestParam("image") MultipartFile imgFile,
			@RequestParam(value = "altText", required = false) String altText
	) {
		return getImageResponse(imgFile, altText, image -> {
			imageService.add(image);
			return new ResponseEntity<>(image.getImageId(), HttpStatus.CREATED);
		});
	}


	/**
	 * Removes an image from the collection.
	 *
	 * @param id The id of image to be removed.
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		return new ResponseEntity<>(imageService.deleteImage(id)
				? HttpStatus.NO_CONTENT
				: HttpStatus.NOT_FOUND
		);
	}

	/**
	 * Update image in the repository.
	 *
	 * @param id      The id of the image to update.
	 * @param imgFile New image data to store.
	 * @return 200 OK status on success, 400 Bad request on error
	 */
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> update(
			@PathVariable int id,
			@RequestParam("image") MultipartFile imgFile,
			@RequestParam(value = "altText", required = false) String altText
	) {
		return getImageResponse(imgFile, altText, image ->
				new ResponseEntity<>(imageService.update(id, image)
						? HttpStatus.NO_CONTENT
						: HttpStatus.NOT_FOUND
				)
		);
	}
}
