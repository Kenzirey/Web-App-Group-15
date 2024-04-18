package no.ntnu.database.controllers;

import java.util.Optional;
import no.ntnu.database.entities.Course;
import no.ntnu.database.services.CourseService;
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
 * REST controller for administrative operations related to courses.
 * Provides endpoints for creating, updating, deleting, and retrieving course information.
 */
@RestController
@RequestMapping("/admin/courses")
public class AdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final CourseService courseService;


	/**
	 * Constructs the AdminController with the courseService.
	 *
	 * @param courseService The service that will handle business logic for courses.
	 */
	@Autowired
	public AdminController(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * Adds a new course to the system.
	 *
	 * @param course The course information to add.
	 * @return A response entity indicating the outcome of the operation.
	 */
	@PostMapping
	public ResponseEntity<String> addCourse(@RequestBody Course course) {
		try {
			courseService.add(course);
			LOGGER.info("Course added successfully: {}", course.getCourseName());
			return ResponseEntity.status(HttpStatus.CREATED).body("Course added successfully");
		} catch (Exception e) {
			LOGGER.error("Error adding course", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding course");
		}
	}

	/**
	 * Removes a course from the system by ID.
	 *
	 * @param id The ID of the course to remove.
	 * @return A response entity indicating the outcome of the operation.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeCourse(@PathVariable int id) {
		if (courseService.delete(id)) {
			LOGGER.info("Course removed successfully: {}", id);
			return ResponseEntity.ok("Course removed successfully");
		} else {
			LOGGER.warn("Course not found: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
		}
	}


	/**
	 * Updates the information of an existing course by ID.
	 *
	 * @param id     The ID of the course to update.
	 * @param course The new information for the course.
	 * @return A response entity indicating the outcome of the operation.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCourse(@PathVariable int id, @RequestBody Course course) {
		try {
			courseService.updateCourse(id, course);
			LOGGER.info("Course updated successfully: {}", course.getCourseName());
			return ResponseEntity.ok("Course updated successfully");
		} catch (Exception e) {
			LOGGER.error("Error updating course: {}", id, e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating course");
		}
	}

	/**
	 * Retrieves all courses from the system.
	 *
	 * @return A list of all courses.
	 */
	@GetMapping
	public ResponseEntity<Iterable<Course>> getAllCourses() {
		Iterable<Course> courses = courseService.getAllCourses();
		LOGGER.info("Retrieved all courses");
		return ResponseEntity.ok(courses);
	}

	/**
     * Retrieves a single course by its ID.
     *
     * @param id The ID of the course to retrieve.
     * @return The requested course if found, or an error response otherwise.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourse(@PathVariable int id) {
		Optional<Course> course = courseService.findById(id);
		course.ifPresentOrElse(
				c -> LOGGER.info("Retrieved course: {}", c.getCourseName()),
				() -> LOGGER.warn("Course not found: {}", id)
		);
		return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
