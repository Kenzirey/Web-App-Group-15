package no.ntnu.database.controllers;

import no.ntnu.database.entities.Favorite;
import no.ntnu.database.entities.User;
import no.ntnu.database.services.CourseService;
import no.ntnu.database.services.FavoriteService;
import no.ntnu.database.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST API controller for favorite collection.
 */
@CrossOrigin
@RestController
@RequestMapping("/favorites")
public class FavoriteController {
	private final FavoriteService favoriteService;
	private final UserService userService;
	private final CourseService courseService;


	/**
	 * Makes the favorite controller.
	 *
	 * @param favoriteService The service class for communication
	 */
	public FavoriteController(
			@Autowired FavoriteService favoriteService,
			@Autowired UserService userService,
			@Autowired CourseService courseService
	) {
		this.favoriteService = favoriteService;
		this.userService = userService;
		this.courseService = courseService;
	}

	/**
	 * Gets the currently authenticated {@link User}.
	 *
	 * @return The currently authenticated user
	 */
	private User getUser() {
		return userService.findByUsername(SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName()
		).orElseThrow(() -> new IllegalStateException(
				"Favorite method accessed with invalid user authentication"
		));
	}


	/**
	 * Returns all favorite entries for the authenticated {@link User}.
	 *
	 * @return All favorite entries for the authenticated user
	 */
	@GetMapping
	public Iterable<Favorite> getAllFavorites() {
		return favoriteService.getAllFavourites(getUser().getId());
	}

	/**
	 * Endpoint to find a specific favorite entry for the authorized {@link User}.
	 *
	 * @param courseId The id of the {@link no.ntnu.database.entities.Course Course}
	 *                 in the favorite entry
	 * @return {@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A favorite entry for the authorized user with course that matches id,
	 *             returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@GetMapping("/{courseId}")
	public ResponseEntity<Favorite> getFavorite(@PathVariable int courseId) {
		return favoriteService.findById(courseId, getUser().getId())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Adds a favorite entry for a the authorized {@link User}.
	 *
	 * @param courseId The ID of the {@link no.ntnu.database.entities.Course Course}
	 *                 to include in the entry
	 * @return 201 CREATED status on success, 400 BAD REQUEST on error
	 */
	@PostMapping("/{courseId}")
	public ResponseEntity<Favorite> add(@PathVariable int courseId) {
		return courseService
				.findById(courseId)
				.map(course -> new Favorite(course, getUser()))
				.map(favorite -> {
					favoriteService.add(favorite);
					return new ResponseEntity<>(favorite, HttpStatus.CREATED);
				})
				.orElse(ResponseEntity.badRequest().build());
	}


	/**
	 * Removes a favorite entry for the authorized {@link User}.
	 *
	 * @param courseId The id of the {@link no.ntnu.database.entities.Course Course}
	 *                 in the entry to remove
	 * @return 200 OK status on success, 404 NOT FOUND on error
	 */
	@DeleteMapping("/{courseId}")
	public ResponseEntity<String> delete(@PathVariable int courseId) {
		return favoriteService.delete(courseId, getUser().getId())
				? ResponseEntity.ok().build()
				: ResponseEntity.notFound().build();
	}
}

