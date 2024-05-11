package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import no.ntnu.database.entities.CourseProvider;
import no.ntnu.database.services.CourseProviderService;
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

	/**
	 * Makes the course provider controller via autowired.
	 *
	 * @param service the service class for communication.
	 */
	@Autowired
	public CourseProviderController(CourseProviderService service) {
		this.service = service;
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
	@GetMapping(value = "/search/{query}", produces = {"application/json"})
	public Iterable<CourseProvider> searchCategory(@PathVariable String query) {
		return query == null || query.isBlank()
				? service.getAllProviders()
				: service.searchProvider(query);
	}
}
