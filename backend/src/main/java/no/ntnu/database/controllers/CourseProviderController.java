package no.ntnu.database.controllers;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

import no.ntnu.database.entities.CourseProvider;
import no.ntnu.database.services.CourseProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 */
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
	@GetMapping
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
	@GetMapping("/{id}")
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
	 * HTTP POST endpoint for adding a new course provider.
	 *
	 * @param provider 	the data of the {@link CourseProvider} being added.
	 * @return 			<p>Returns a {@link ResponseEntity} with status 201 if successfully
	 * 					created and the value of the new ID.</p>
	 * 					<p>Returns a {@link ResponseEntity} with status 401 for bad request
	 * 					if data is illegal or inappropriate.</p>
	 */
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
	 * @return 		<p>Returns a {@link ResponseEntity} with status 200 if successfully deleted.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 404
	 * 				if course to be deleted was not found.</p>
	 */
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
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> update(
			@PathVariable int id, @RequestBody CourseProvider provider) {
		ResponseEntity<String> response;
		try {
			service.updateCourseProvider(id, provider);
			//Because there's not a "body" response to client/us when this method is called.
			response = ResponseEntity.noContent().build();
		} catch (EntityNotFoundException e) {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}
}
