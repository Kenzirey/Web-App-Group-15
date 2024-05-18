package no.ntnu.database.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import no.ntnu.database.model.CourseProvider;
import no.ntnu.database.model.CourseProviderLink;
import no.ntnu.database.service.CourseProviderLinkService;
import no.ntnu.database.service.CourseProviderService;
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
 * REST API controller for the course provider collection.
 * Code adapted from the app-dev repository by Gist.
 */
@CrossOrigin
@RestController
@RequestMapping("/providers")
public class CourseProviderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseProviderController.class);

	private final CourseProviderService service;
	private final CourseProviderLinkService linkService;

	/**
	 * Makes the course provider controller via autowired.
	 *
	 * @param service the service class for communication.
	 */
	@Autowired
	public CourseProviderController(CourseProviderService service,
									CourseProviderLinkService linkService) {
		this.service = service;
		this.linkService = linkService;
	}

	/**
	 * Returns all the course providers in the database.
	 *
	 * @return all the course providers in the database.
	 */
	@Operation(
			summary = "Get all course providers",
			description = "Returns a list of all existing course providers in the database"
	)
	@ApiResponse(responseCode = "200", description = "Retrieved list successfully")
	@GetMapping(produces = {"application/json"})
	public Iterable<CourseProvider> getAllCourseProviders() {
		LOGGER.info("Getting all providers, test!");
		return service.getAllProviders();
	}


	/**
	 * Endpoint to search for a specific course provider.
	 *
	 * @param id 	the id of the course provider to search for.
	 * @return 		{@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A corresponding course provider that matches id, returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@Operation(
			summary = "Get a course provider via its id",
			description = "Returns one single course provider matching the given id"
	)
	@ApiResponse(
			responseCode = "200", description = "A corresponding course provider was found")
	@ApiResponse(
			responseCode = "404", description = "A corresponding course provider was not found")
	@GetMapping(value = "/{id}", produces = {"application/json"})
	public ResponseEntity<CourseProvider> getProvider(@PathVariable int id) {
		ResponseEntity<CourseProvider> response;
		Optional<CourseProvider> provider = service.findById(id);
		if (provider.isPresent()) {
			response = ResponseEntity.ok(provider.get());
		} else {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * HTTP POST endpoint for adding a new {@link CourseProvider}.
	 *
	 * @param provider 	the data of the {@link CourseProvider} being added.
	 *
	 * @return 			<p>Returns a {@link ResponseEntity} with status 201 if successfully
	 * 					created and the value of the new ID.</p>
	 * 					<p>Returns a {@link ResponseEntity} with status 400 for bad request
	 * 					if data is illegal or inappropriate.</p>
	 * 					<p>Returns a {@link ResponseEntity} with status 403 for unauthorized access
	 * 					if incorrect level of authorization.</p>
	 */
	@Operation(
			summary = "Adds a new course provider",
			description = "Creates a new course provider with the data provided"
	)
	@ApiResponse(
			responseCode = "201", description = "A course provider was successfully added")
	@ApiResponse(
			responseCode = "400", description = "Course provider failed to be added, bad request")
	@ApiResponse(
			responseCode = "403", description = "Forbidden, not authorized")
	@PostMapping
	public ResponseEntity<String> add(@RequestBody CourseProvider provider) {
		ResponseEntity<String> response;
		try {
			int id = service.add(provider);
			response = ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(id));
		} catch (IllegalArgumentException ia) {
			response = ResponseEntity.badRequest().body(ia.getMessage());
		}
		return response;
	}

	/**
	 * Deletes a course provider from the collection.
	 *
	 * @param id 	The id of the course provider to be removed from the collection.
	 *
	 * @return 		<p>Returns a {@link ResponseEntity} with status 200 if successfully deleted.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 403 if incorrect level
	 * 				of authorization.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 404
	 * 				if course to be deleted was not found.</p>
	 */
	@Operation(
			summary = "Deletes a course provider",
			description = "Deletes the course provider corresponding to the given id"
	)
	@ApiResponse(responseCode = "200", description = "Course provider was successfully deleted")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@ApiResponse(responseCode = "404", description = "Course provider was not found")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		ResponseEntity<String> response;
		if (service.delete(id)) {
			//Should we use the simplified syntax like above, or this way?
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	/**
	 * Updates an existing course provider in the repository.
	 *
	 * @param id       the id of the course provider to be updated.
	 * @param provider the new course provider information to be
	 *                 stored from the {@link RequestBody}.
	 *
	 * @return 		<p>a {@link ResponseEntity} with code 204 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 400 if illegal argument is provided.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 403 if incorrect level
	 * 				of authorization.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(
			summary = "Updates an existing course provider",
			description = "Updates the course provider corresponding to the given id"
	)
	@ApiResponse(responseCode = "204", description = "Course successfully updated")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@ApiResponse(responseCode = "404", description = "Course provider was not found")
	@PutMapping("/{id}")
	public ResponseEntity<String> update(
			@PathVariable int id, @RequestBody CourseProvider provider) {
		ResponseEntity<String> response;
		try {
			service.updateCourseProvider(id, provider);
			response = ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			response = ResponseEntity.badRequest().build();
		} catch (EntityNotFoundException e) {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * Endpoint to search for providers.
	 *
	 * @param query The query to use when searching for providers.
	 *
	 * @return Providers that match the search query.
	 */
	@Operation(summary = "Search for provider(s)",
			description = "search for provider(s) via a query")
	@ApiResponse(responseCode = "200", description = "Successfully searched")
	@GetMapping(value = "/search/{query}", produces = {"application/json"})
	public Iterable<CourseProvider> searchProvider(@PathVariable String query) {
		return query == null || query.isBlank()
				? service.getAllProviders()
				: service.searchProvider(query);
	}

	/**
	 * Get a specific link via its {@link CourseProvider}- and course ids.
	 *
	 * @param providerId 	the linked {@link CourseProvider}'s unique id.
	 * @param courseId		the course's unique id.
	 *
	 * @return 		<p>a {@link ResponseEntity} with code 200 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(summary = "Get a course provider link",
			description = "Returns details of a specific course provider link")
	@ApiResponse(responseCode = "200", description = "Successfully retrieved")
	@ApiResponse(responseCode = "404", description = "Course provider link not found")
	@GetMapping("{providerId}/coursePriceListings/{courseId}")
	public ResponseEntity<CourseProviderLink> getCourseProviderLink(@PathVariable int providerId,
																	@PathVariable int courseId) {
		Optional<CourseProviderLink> link =
				linkService.findCourseProviderLink(providerId, courseId);
		return link.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Updates a course provider link.
	 *
	 * @param providerId 	the linked {@link CourseProvider}'s id.
	 * @param courseId		the linked course's id.
	 * @param updatedLink	the updated link with new price.
	 *
	 * @return 		<p>a {@link ResponseEntity} with code 204 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 400 if illegal argument is provided.</p>
	 * 				<p>a {@link ResponseEntity} with status 403 if incorrect level
	 * 											of authorization.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(summary = "Update a course provider link",
			description = "Updates an existing course provider link with new information")
	@ApiResponse(responseCode = "204", description = "Successfully updated the link")
	@ApiResponse(responseCode = "400", description = "Invalid data provided")
	@ApiResponse(responseCode = "404", description = "Course provider link not found")
	@PutMapping("{providerId}/coursePriceListings/{courseId}")
	public ResponseEntity<String> updateCourseProviderLink(@PathVariable int providerId,
														   @PathVariable int courseId,
														   @RequestBody
															   CourseProviderLink updatedLink) {
		try {
			linkService.updateCourseProviderLink(providerId, courseId, updatedLink);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Bad request: " + e.getMessage());
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course provider link not found.");
		}
	}

	/**
	 * Adds a price listing based on which {@link CourseProvider} the course has.
	 *
	 * @param providerId the {@link CourseProvider}'s id.
	 * @param dto        the data transfer object containing course and price information.
	 *
	 * @return 		<p>a {@link ResponseEntity} with code 201 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 400 if illegal argument is provided.</p>
	 * 				<p>a {@link ResponseEntity} with status 403 if incorrect level
	 * 											of authorization.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(summary = "Adds a price listing for a course provider",
			description = "Adds a new price listing for a specific course provider")
	@ApiResponse(responseCode = "201", description = "Price listing successfully added")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, incorrect authorization")
	@ApiResponse(responseCode = "404", description = "Entity not found")
	@PostMapping("/{providerId}/coursePriceListings")
	public ResponseEntity<String> addCoursePriceListing(@PathVariable int providerId,
														@RequestBody CourseProviderLink
																.CourseProviderLinkDto dto) {
		try {
			linkService.addCourseListing(providerId, dto);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Course's price listing added successfully");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Course or provider not found.");
		}
	}

	/**
	 * Deletes an existing price listing for a course provider.
	 *
	 * @param providerId the {@link CourseProvider}'s id.
	 * @param courseId   the course's id to remove the listing from.
	 *
	 * @return 		<p>a {@link ResponseEntity} with code 204 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 400 if illegal argument is provided.</p>
	 * 				<p>a {@link ResponseEntity} with status 403 if incorrect level
	 * 											of authorization.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(summary = "Deletes a price listing",
			description = "Deletes a price listing for a specific course provider")
	@ApiResponse(responseCode = "204", description = "Price listing successfully deleted")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, incorrect authorization")
	@ApiResponse(responseCode = "404", description = "Entity not found")
	@DeleteMapping("/{providerId}/coursePriceListings/{courseId}")
	public ResponseEntity<String> deleteCoursePriceListing(
			@PathVariable int providerId,
			@PathVariable int courseId) {
		try {
			boolean deleted = linkService.deleteCourseListing(providerId, courseId);
			if (deleted) {
				return ResponseEntity.noContent().build();
			} else {
				String message = String.format(
						"Course listing not found for providerId: %d and courseId: %d",
						providerId, courseId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
			}
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Bad request: " + e.getMessage());
		}
	}

}
