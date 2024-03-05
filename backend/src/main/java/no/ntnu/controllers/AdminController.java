package no.ntnu.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.dto.Course;
import no.ntnu.service.AdminRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling administrative actions related to course management.
 */
@RestController
@RequestMapping("/admin")
@Tag(
		name = "AdminController",
		description = "API for course management administrative tasks"
)
public class AdminController {
	private static final Logger LOGGER = Logger.getLogger(AdminController.class.getName());
	private static final String SQL_ERROR_MESSAGE = "SQLException occurred";

	private final AdminRequests requests;

	/**
	 * Creates the controller.
	 *
	 * @param adminRequests Autowired object for sending requests to the database
	 */
	@Autowired
	public AdminController(AdminRequests adminRequests) {
		this.requests = adminRequests;
	}

	/**
	 * Endpoint for adding a new course.
	 *
	 * @param course The course to add
	 * @return A {@link ResponseEntity} representing an HTTP response
	 */
	@Operation(
			summary = "Add a new course",
			description = "Adds a new course to the system."
	)
	@ApiResponse(
			responseCode = "201",
			description = "Course added successfully",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "400",
			description = "Course already exists",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "500",
			description = "Something went wrong when attempting to add the course",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@PostMapping("/courses")
	public ResponseEntity<String> addCourse(@RequestBody Course course) {
		ResponseEntity<String> response;
		try {
			if (requests.insertCourse(course)) {
				response = new ResponseEntity<>(
						"Course added successfully",
						HttpStatus.CREATED
				);
			} else {
				response = new ResponseEntity<>(
						"Course already exists",
						HttpStatus.BAD_REQUEST
				);
			}
		} catch (SQLException sqle) {
			LOGGER.log(Level.WARNING, SQL_ERROR_MESSAGE, sqle);
			response = new ResponseEntity<>(
					"Something went wrong when attempting to add the course",
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		return response;
	}

	/**
	 * Endpoint for removing an existing course.
	 *
	 * @param id The ID of the course to remove
	 * @return A {@link ResponseEntity} representing an HTTP response
	 */
	@Operation(
			summary = "Remove a course",
			description = "Removes a course from the system by ID."
	)
	@ApiResponse(
			responseCode = "200",
			description = "Course removed successfully",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "400",
			description = "Failed does not exist / is already removed",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "500",
			description = "Something went wrong when attempting to remove the course",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<String> removeCourse(@PathVariable int id) {
		ResponseEntity<String> response;
		try {
			if (requests.removeCourse(id)) {
				response = new ResponseEntity<>(
						"Course removed successfully",
						HttpStatus.OK
				);
			} else {
				response = new ResponseEntity<>(
						"Failed does not exist / is already removed",
						HttpStatus.BAD_REQUEST
				);
			}
		} catch (SQLException sqle) {
			LOGGER.log(Level.WARNING, SQL_ERROR_MESSAGE, sqle);
			response = new ResponseEntity<>(
					"Something went wrong when attempting to remove the course",
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		return response;
	}

	/**
	 * Endpoint for updating an existing course.
	 *
	 * @param id The ID of the course to update
	 * @param course The new & updated course
	 * @return A {@link ResponseEntity} representing an HTTP response
	 */
	@Operation(
			summary = "Update a course",
			description = "Updates the details of an existing course."
	)
	@ApiResponse(
			responseCode = "200",
			description = "Course updated successfully",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "400",
			description = "Course is already up-to-date with the provided info",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@ApiResponse(
			responseCode = "500",
			description = "Something went wrong when attempting to update the course "
					+ "(Does it exist?)",
			content = @Content(schema = @Schema(implementation = String.class))
	)
	@PutMapping("/courses/{id}")
	public ResponseEntity<String> updateCourse(
			@PathVariable int id,
			@RequestBody Course course
	) {
		ResponseEntity<String> response;
		try {
			if (requests.updateCourse(id, course)) {
				response = new ResponseEntity<>(
						"Course added successfully",
						HttpStatus.OK
				);
			} else {
				response = new ResponseEntity<>(
						"Course already exists",
						HttpStatus.BAD_REQUEST
				);
			}
		} catch (SQLException sqle) {
			LOGGER.log(Level.WARNING, SQL_ERROR_MESSAGE, sqle);
			response = new ResponseEntity<>(
					"Something went wrong when attempting to update the course (Does it exist?)",
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		return response;
	}
}
