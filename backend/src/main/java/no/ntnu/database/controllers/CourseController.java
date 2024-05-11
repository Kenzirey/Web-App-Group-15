package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Optional;
import java.util.Set;
import no.ntnu.database.entities.Category;
import no.ntnu.database.entities.Course;
import no.ntnu.database.services.CourseService;
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
 * REST API controller for course collection.
 * Code adapted from app-dev repository by Gist.
 */
@CrossOrigin
@RestController
@RequestMapping("/courses")
public class CourseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	private final CourseService courseService;

	/**
	 * Makes the course controller.
	 *
	 * @param courseService The service class for communication
	 */
	@Autowired
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Returns all the courses in the database.
	 *
	 * @return all the courses in the database.
	 */
	@Operation(
			summary = "Get all courses",
			description = "Returns a list of all existing courses in the database"
	)
	@ApiResponse(responseCode = "200", description = "Retrieved list successfully")
	@GetMapping(produces = {"application/json"})
	public Iterable<Course> getAllCourses() {
		LOGGER.info("Getting all courses, test!");
		return courseService.getAllCourses();
	}

	/**
	 * Endpoint to search for a specific course.
	 *
	 * @param id 	the id of the course to search for.
	 * @return 		{@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A corresponding course that matches id, returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@Operation(
			summary = "Get a course via its id",
			description = "Returns one single course matching the given id"
	)
	@ApiResponse(
			responseCode = "200", description = "A corresponding course was successfully found")
	@ApiResponse(
			responseCode = "404", description = "A course with a corresponding id was not found")
	@GetMapping(value = "/{id}", produces = {"application/json"})
	public ResponseEntity<Course> getCourse(@PathVariable Integer id) {
		ResponseEntity<Course> response;
		Optional<Course> course = courseService.findById(id);
		if (course.isPresent()) {
			response = ResponseEntity.ok(course.get());
		} else {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * HTTP POST endpoint for adding a new course.
	 *
	 * @param course 	the data of the {@link Course} being added.
	 * @return 			<p>Returns a {@link ResponseEntity} with status 201 if successfully
	 * 					created and the value of the new ID.</p>
	 * 					<p>Returns a {@link ResponseEntity} with status 401 for bad request
	 * 					if data is illegal or inappropriate.</p>
	 * 					<p>Returns a {@link ResponseEntity} with status 403 for unauthorized access
	 * 					if incorrect level of authorization.</p>
	 */
	@Operation(
			summary = "Add a new course",
			description = "Creates a new course with course data provided"
	)
	@ApiResponse(responseCode = "201", description = "A course was successfully added")
	@ApiResponse(responseCode = "400", description = "Course failed to be added, bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Course course) {
		ResponseEntity<String> response;
		try {
			int id = courseService.add(course);
			response = new ResponseEntity<>(String.valueOf(id), HttpStatus.CREATED);
		} catch (IllegalArgumentException ia) {
			response = new ResponseEntity<>(ia.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	/**
	 * Deletes a course from the collection.
	 *
	 * @param id 	The id of the course to be removed from the collection.
	 * @return 		<p>Returns a {@link ResponseEntity} with status 200 if successfully deleted.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 403 if unauthorized user.</p>
	 * 				<p>Returns a {@link ResponseEntity} with status 404
	 * 				if course to be deleted was not found.</p>
	 */
	@Operation(
			summary = "Deletes a course",
			description = "Deletes the course corresponding to the given id"
	)
	@ApiResponse(responseCode = "200", description = "Course successfully deleted")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@ApiResponse(responseCode = "404", description = "Course was not found")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		ResponseEntity<String> response;
		if (courseService.delete(id)) {
			response = new ResponseEntity<>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	/**
	 * Updates an existing course in the repository.
	 *
	 * @param id 		the id of the course to be updated.
	 * @param course 	the new course information to be stored from the {@link RequestBody}.
	 * @return 		<p>a {@link ResponseEntity} with code 204 on success.</p>
	 * 				<p>a {@link ResponseEntity} with code 400 if illegal argument is provided.</p>
	 * 				<p>a {@link ResponseEntity} with code 403 if unauthorized user.</p>
	 * 				<p>a {@link ResponseEntity} with code 404 if course to update is not found.</p>
	 */
	@Operation(
			summary = "Update an existing course",
			description = "Updates the course corresponding to the given id"
	)
	@ApiResponse(responseCode = "204", description = "Course updated")
	@ApiResponse(responseCode = "400", description = "Bad request")
	@ApiResponse(responseCode = "403", description = "Forbidden, not authorized")
	@ApiResponse(responseCode = "404", description = "Course was not found")
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable int id, @RequestBody Course course) {
		ResponseEntity<String> response;
		try {
			courseService.updateCourse(id, course);
			//Because there's no response to client/us when this method is called.
			response = ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			response = ResponseEntity.badRequest().build();
		} catch (IllegalStateException e) {
			LOGGER.warn(e.getMessage());
			response = ResponseEntity.notFound().build();
		}
		return  response;
	}

	/**
	 * Endpoint to search for courses.
	 *
	 * @param query The query to use when searching for courses.
	 *
	 * @return Courses that match the search query
	 */
	@Operation(summary = "Search for courses",
			description = "Search for courses via a string query")
	@ApiResponse(responseCode = "200", description = "Successful request")
	@GetMapping(value = "/search/{query}", produces = {"application/json"})
	public Iterable<Course> searchCategory(@PathVariable String query) {
		return query == null || query.isBlank()
				? courseService.getAllCourses()
				: courseService.searchCourse(query);
	}

}
