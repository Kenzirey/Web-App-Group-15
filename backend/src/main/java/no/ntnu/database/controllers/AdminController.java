package no.ntnu.database.controllers;

import java.util.List;
import java.util.Optional;
import no.ntnu.database.entities.Course;
import no.ntnu.database.entities.User;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.database.services.CourseService;
import no.ntnu.database.services.UserService;

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
 * REST controller for administrative operations related to courses and users.
 * Provides endpoints for creating, updating, deleting, and retrieving course and user information.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private final CourseService courseService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(CourseService courseService, UserRepository userRepository, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
	 * Adds a new course to the system.
	 *
	 * @param course The course information to add.
	 * @return A response entity indicating the outcome of the operation.
	 */
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
	 * Removes a course from the system by ID.
	 *
	 * @param id The ID of the course to remove.
	 * @return A response entity indicating the outcome of the operation.
	 */
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
	 * Updates the information of an existing course by ID.
	 *
	 * @param id     The ID of the course to update.
	 * @param course The new information for the course.
	 * @return A response entity indicating the outcome of the operation.
	 */
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

    @GetMapping("/courses/count")
    public ResponseEntity<Long> getCoursesCount() {
        long count = courseService.countAllCourses();
        LOGGER.info("Total number of courses: {}", count);
        return ResponseEntity.ok(count);
    }


    /**
     * Retrieves a single course by its ID.
     *
     * @param id The ID of the course to retrieve.
     * @return The requested course if found, or an error response otherwise.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Optional<Course> course = courseService.findById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
	 * Retrieves the count of users with two-factor authentication enabled.
	 *
	 * @return A ResponseEntity containing the count of users with two-factor authentication enabled.
	 */
    @GetMapping("/users/count/2fa")
    public ResponseEntity<Long> getCountOfUsersWith2FA() {
        long count = userRepository.countByTwoFactorEnabledTrue();
        return ResponseEntity.ok(count);
    }

    /**
	* Retrieves the total number of users.
	*
	* @return ResponseEntity<Long> containing the total number of users
	*/
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
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
	 * Adds a new user.
	 *
	 * @param user The user object to be added.
	 * @return A ResponseEntity containing the saved user object and the HTTP status code 201 (Created).
	 */
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
	* Updates a user with the specified ID.
	*
	* @param id   The ID of the user to update.
	* @param user The updated user object.
	* @return The ResponseEntity containing the updated user object.
	*/
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
	 * Deletes a user with the specified ID.
	 *
	 * @param id the ID of the user to delete
	 * @return a ResponseEntity with no content if the user was successfully deleted
	 */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /**
	 * Retrieves a user by their ID.
	 *
	 * @param id the ID of the user to retrieve
	 * @return the ResponseEntity containing the user if found, or a not found response if the user does not exist
	 */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
	 * Toggles the active status of a user.
	 *
	 * @param id The ID of the user to toggle the active status for.
	 * @param isActive The new active status for the user.
	 * @return A ResponseEntity with a String indicating the result of the operation.
	 */
    @PostMapping("/users/{id}/toggle-active")
    public ResponseEntity<String> toggleUserActive(@PathVariable Long id, @RequestBody boolean isActive) {
        return userService.toggleUserActive(id, isActive);
    }
}
