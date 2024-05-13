package no.ntnu.database.controllers;

import java.util.Optional;
import no.ntnu.database.model.Favorite;
import no.ntnu.database.services.FavoriteService;
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
 * REST API controller for favorite collection.
 */
@CrossOrigin
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteController.class);

	private final FavoriteService favoriteService;


	/**
	 * Makes the favorite controller.
	 *
	 * @param favoriteService The service class for communication
	 */
	public FavoriteController(@Autowired FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}


	/**
	 * Returns all favorite courses in the database.
	 *
	 * @return All courses in the database.
	 */
	@GetMapping
	public Iterable<Favorite> getAllFavorites() {
		LOGGER.info("Getting all favorites");
		return favoriteService.getAllFavourites();
	}

	/**
	 * Endpoint to search for a specific favorite course.
	 *
	 * @param id 	The id of the course to return.
	 * @return 		{@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A corresponding course that matches id, returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Favorite> getFavorite(@PathVariable int id) {
		ResponseEntity<Favorite> response;
		Optional<Favorite> favorite = favoriteService.findByProductId(id);
		if (favorite.isPresent()) {
			response = ResponseEntity.ok(favorite.get());
		} else {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * Adds a favorite course to the collection.
	 *
	 * @param favorite The favorite added
	 * @return 201 CREATED status on success, 400 Bad request on error
	 */
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Favorite favorite) {
		ResponseEntity<String> response;
		try {
			int id = favoriteService.add(favorite);
			response = new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;

	}


	/**
	 * Removes a favorite course from the collection.
	 *
	 * @param id The id of course to be removed.
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		ResponseEntity<String> response;
		if (favoriteService.delete(id)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return  response;
	}


	/**
	 * Update a favorite course in the repository.
	 *
	 * @param id The id of the course  to update.
	 * @param favorite New course data to store.
	 * @return 200 OK status on success, 400 Bad request on error
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody Favorite favorite) {
		ResponseEntity<String> response;
		try {
			favoriteService.updateFavorite(id, favorite);
			response = new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}



}

