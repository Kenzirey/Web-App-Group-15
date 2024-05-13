package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import no.ntnu.database.model.Course;
import no.ntnu.database.model.User;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.database.services.CourseService;
import no.ntnu.database.services.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for administrative operations related to courses and users.
 * Provides endpoints for creating, updating, deleting, and retrieving course and user information.
 */
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private final CourseService courseService;
	private final UserService userService;
	private final UserRepository userRepository;

	/**
	 * Creates an admin controller via Autowired with services and repository.
	 *
	 * @param courseService 	The service for managing business logic of courses.
	 * @param userRepository	The repository for handling database access to user data.
	 * @param userService		The service for managing business logic of users.
	 */
	@Autowired
	public AdminController(CourseService courseService,
						   UserRepository userRepository,
						   UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	/**
	 * Adds a new {@link Course} to the system.
	 *
	 * @param course The {@link Course} information to add.
	 *
	 * @return A {@link ResponseEntity} with code 201 if successfully added,
	 * 									or code 400 if bad request, not added.
	 */
	@Operation(summary = "Add course",
			description = "Adds a new course to the database")
	@ApiResponse(responseCode = "201", description = "Course added successfully")
	@ApiResponse(responseCode = "400", description = "Bad request, course was not added")
	@PostMapping("/courses")
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
	 * Removes a {@link Course} from the system by ID.
	 *
	 * @param id The ID of the {@link Course} to remove.
	 *
	 * @return A {@link ResponseEntity} with code 200 if removed successfully,
	 * 									or code 404 if the course to remove was not found.
	 */
	@Operation(summary = "Remove course",
			description = "Removes a course by its provided id")
	@ApiResponse(responseCode = "200", description = "Course successfully removed")
	@ApiResponse(responseCode = "404", description = "Course to remove was not found")
	@DeleteMapping("/courses/{id}")
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
	 * Updates the information of an existing {@link Course} by ID.
	 *
	 * @param id     The ID of the {@link Course} to update.
	 * @param course The new information for the {@link Course}.
	 *
	 * @return A {@link ResponseEntity} with code 200 on success,
	 * 									or code 404 if the course to update was not found.
	 */
	@Operation(summary = "Update course",
			description = "Updates a course through the provided id")
	@ApiResponse(responseCode = "200", description = "Course successfully updated")
	@ApiResponse(responseCode = "404", description = "Course to update was not found")
	@PutMapping("/courses/{id}")
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
	 * Get the amount of courses in the database.
	 *
	 * @return a {@link ResponseEntity} with code 200 and amount of courses.
	 */
	@Operation(summary = "Get amount of courses",
			description = "Get the amount of courses currently in the database")
	@ApiResponse(responseCode = "200", description = "Retrieved courses")
	@GetMapping("/courses/count")
	public ResponseEntity<Long> getCoursesCount() {
		long count = courseService.countAllCourses();
		LOGGER.info("Total number of courses: {}", count);
		return ResponseEntity.ok(count);
	}


	/**
	 * Retrieves a single {@link Course} by its ID.
	 *
	 * @param id The ID of the {@link Course} to retrieve.
	 *
	 * @return a {@link ResponseEntity} with code 200 on success,
	 * 									or code 404 if the course was not found.
	 */
	@Operation(summary = "Get course",
			description = "Get a course by the provided id")
	@ApiResponse(responseCode = "200", description = "Course was found")
	@ApiResponse(responseCode = "404", description = "Course not found")
	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable int id) {
		Optional<Course> course = courseService.findById(id);
		return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Retrieves the count of users with two-factor authentication enabled.
	 *
	 * @return A {@link ResponseEntity} containing the count of users
	 * 									with two-factor authentication enabled.
	 */
	@Operation(summary = "Get 2Fa user count",
			description = "Get the amount of users with 2-factor authentication enabled")
	@ApiResponse(responseCode = "200", description = "Amount of users returned")
	@GetMapping("/users/count/2fa")
	public ResponseEntity<Long> getCountOfUsersWith2Fa() {
		long count = userRepository.countByTwoFactorEnabledTrue();
		return ResponseEntity.ok(count);
	}

	/**
	 * Retrieves the total number of users.
	 *
	 * @return ResponseEntity containing the total number of users
	 */
	@Operation(summary = "Get amount of users",
			description = "Get the amount of registered users currently in the database")
	@ApiResponse(responseCode = "200", description = "Amount of users returned")
	@GetMapping("/users/count")
	public ResponseEntity<Long> getTotalUsersCount() {
		long count = userRepository.count();
		LOGGER.info("Total number of users: {}", count);
		return ResponseEntity.ok(count);
	}

	/**
	 * Retrieves a list of all users.
	 *
	 * @return A list of User objects representing all users.
	 */
	@Operation(summary = "Get users",
			description = "Get all the users currently in the database")
	@ApiResponse(responseCode = "200", description = "Users retrieved")
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}

	/**
	 * Adds a new user.
	 *
	 * @param user The {@link User} object to be added.
	 *
	 * @return A {@link ResponseEntity} containing the saved user object
	 * 								and the HTTP status code 201 (Created).
	 */
	@Operation(summary = "Add user",
			description = "Adds a new user to the database")
	@ApiResponse(responseCode = "201", description = "User created")
	@ApiResponse(responseCode = "404", description = "User not found")
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		ResponseEntity<User> response;
		try {
			User savedUser = userService.saveUser(user);
			response = ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
		} catch (IllegalStateException ise) {
			LOGGER.warn(ise.getMessage());
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * Updates a {@link User} with the specified ID.
	 *
	 * @param id   The ID of the {@link User} to update.
	 * @param user The updated {@link User} object.
	 *
	 * @return The {@link ResponseEntity} containing the updated user object with code 200,
	 * 									or code 404 if user is not found.
	 */
	@Operation(summary = "Update user",
			description = "Updates an existing user")
	@ApiResponse(responseCode = "200", description = "User updated")
	@ApiResponse(responseCode = "404", description = "User not found")
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		ResponseEntity<User> response;
		try {
			User updatedUser = userService.updateUser(id, user);
			response = ResponseEntity.ok(updatedUser);
		} catch (IllegalStateException ise) {
			LOGGER.warn(ise.getMessage());
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * Deletes a {@link User} with the specified ID.
	 *
	 * @param id the ID of the {@link User} to delete
	 *
	 * @return a {@link ResponseEntity} with code 204 if successfully removed,
	 * 									or code 404 if user to delete was not found.
	 */
	@Operation(summary = "Delete user",
			description = "Deletes a user from the database")
	@ApiResponse(responseCode = "204", description = "User deleted")
	@ApiResponse(responseCode = "404", description = "User not found")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		ResponseEntity<Void> response;
		try {
			userService.deleteUser(id);
			response = ResponseEntity.noContent().build();
		} catch (IllegalStateException ise) {
			LOGGER.warn(ise.getMessage());
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	/**
	 * Retrieves a {@link User} by their ID.
	 *
	 * @param id the ID of the {@link User} to retrieve
	 *
	 * @return the {@link  ResponseEntity} containing the user with code 200 if found,
	 * 									or code 404 if user is not found.
	 */
	@Operation(summary = "Get user",
			description = "Get a user by the provided id")
	@ApiResponse(responseCode = "200", description = "User found successfully")
	@ApiResponse(responseCode = "404", description = "User not found")
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Toggles the active status of a user.
	 *
	 * @param id       The ID of the user to toggle the active status for.
	 * @param isActive The new active status for the user.
	 *
	 * @return A {@link ResponseEntity} with code 200 on success,
	 * 									or code 404 if not found.
	 */
	@Operation(summary = "Toggle state of user",
			description = "Toggle the current active state of a user")
	@ApiResponse(responseCode = "200", description = "User status toggled")
	//This is from user service class.
	@ApiResponse(responseCode = "404", description = "User not found")
	@PostMapping("/users/{id}/toggle-active")
	public ResponseEntity<String> toggleUserActive(@PathVariable Long id,
												   @RequestBody boolean isActive) {
		return userService.toggleUserActive(id, isActive);
	}
}
