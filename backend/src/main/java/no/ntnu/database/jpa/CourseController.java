package no.ntnu.database.jpa;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for course collection.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	private CourseService courseService;

	/**
	 * Returns all the courses in the database.
	 *
	 * @return all the courses in the database.
	 */
	@GetMapping
	public Iterable<Course> getAllCourses() {
		LOGGER.info("Getting all courses, test!");
		return courseService.getAllCourses();
	}

	/**
	 * Endpoint to search for a specific course.
	 * <p>Returns a list of results that match the search criteria</p>
	 *
	 * @param id the id of the course to search for.
	 *
	 * @return {@link ResponseEntity} object containing either:
	 *     <ul>
	 *         <li>A corresponding course that matches id, returns status 200.</li>
	 *         <li>If no match is found, returns status 404.</li>
	 *     </ul>
	 */
	@GetMapping("/{id}")
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

	//TODO: POST method for adding a new course.
	//TODO: DELETE method for deleting a course.
	//TODO: PUT method for updating a course.

}
