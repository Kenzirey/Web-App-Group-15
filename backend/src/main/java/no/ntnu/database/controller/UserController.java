package no.ntnu.database.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.database.model.User;
import no.ntnu.database.service.UserService;
import no.ntnu.dto.ChangePasswordDto;
import no.ntnu.dto.UserRegistrationDto;
import no.ntnu.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

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
 * REST API controller for the user collection.
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * Autowired constructor for creating the user controller.
	 *
	 * @param userService the service responsible for user operations.
	 */
	@Autowired
	public UserController(UserService userService) {
		this. userService = userService;
	}

	/**
	 * Endpoint for registering a new user to the database.
	 *
	 * @param userDto the Data Transfer Object (DTO) of the user registration information.
	 *
	 * @return {@link ResponseEntity} 	with either code 200 for success,
	 * 									or code 400 for bad request (such as already existing).
	 */
	@Operation(summary = "Registers a new user",
			description = "Registers one user to the database")
	@ApiResponse(responseCode = "200", description = "User registered successfully")
	@ApiResponse(responseCode = "400", description = "Bad request, not registered")
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) {
		return userService.registerNewUser(userDto);
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
	@GetMapping("/count/2fa")
	public ResponseEntity<Long> getCountOfUsersWith2Fa() {
		long count = userService.countUsersWithTwoFactorEnabled();
		return ResponseEntity.ok(count);
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> getCountOfUsers() {
		long count = userService.countAllUsers();
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
	@GetMapping
	public List<User> getAllUsers() {
		return userService.findAllUsers();
	}

	/**
	 * Updates a {@link User} with the specified ID.
	 *
	 * @param id   The ID of the {@link User} to update.
	 * @param userUpdateDto The {@link UserUpdateDto} containing updated user details.
	 *
	 * @return The {@link ResponseEntity} containing the updated user object with code 200.
	 */
	@Operation(summary = "Update user",
			description = "Updates an existing user")
	@ApiResponse(responseCode = "200", description = "User updated")
	@ApiResponse(responseCode = "404", description = "User not found")
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(
			@PathVariable Long id,
			@RequestBody UserUpdateDto userUpdateDto
	) {
		ResponseEntity<User> response;
		try {
			User updatedUser = userService.updateUser(id, userUpdateDto);
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
	 * @return a {@link ResponseEntity} with code 204 if successfully removed
	 */
	@Operation(summary = "Delete user",
			description = "Deletes a user from the database")
	@ApiResponse(responseCode = "204", description = "User deleted")
	@ApiResponse(responseCode = "404", description = "User not found")
	@DeleteMapping("/{id}")
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
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		Optional<User> user = userService.findUserById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	/**
	 * Toggles the active status of a user.
	 *
	 * @param id       The ID of the user to toggle the active status for.
	 * @param isActive The new active status for the user.
	 *
	 * @return A {@link ResponseEntity} with code 200 on success,
	 *     or code 404 if not found.
	 */
	@Operation(summary = "Toggle state of user",
			description = "Toggle the current active state of a user")
	@ApiResponse(responseCode = "200", description = "User status toggled")
	@ApiResponse(responseCode = "404", description = "User not found")
	@PostMapping("/{id}/toggle-active")
	public ResponseEntity<String> toggleUserActive(
			@PathVariable Long id,
			@RequestBody boolean isActive
	) {
		ResponseEntity<String> response;
		if (userService.toggleUserActive(id, isActive)) {
			response = ResponseEntity.ok(
					"User " + (isActive ? "activated" : "deactivated") + " successfully."
			);
		} else {
			response = ResponseEntity.notFound().build();
		}
		return response;
	}


	/**
	 * Changes the password for a specific user identified by their ID.
	 *
	 * @param id The ID of the user whose password is to be changed.
	 * @param passwordDto The DTO containing the current and new password data.
	 * @return A {@link ResponseEntity} containing a success message or an error message.
	 */
	@Operation(summary = "Change user password",
		description = "Changes the password if the current password matches the existing one.")
	@ApiResponse(responseCode = "200", 
		description = "Password updated successfully.")
	@ApiResponse(responseCode = "400",
		description = "Password update failed. Incorrect current password.")
	@PostMapping("/{id}/change-password")
	public ResponseEntity<String> changePassword(
		@PathVariable Long id, @RequestBody ChangePasswordDto passwordDto) {
		try {
			boolean success = userService.changeUserPassword(
				id,
			 	passwordDto.getCurrentPassword(),
			  	passwordDto.getNewPassword());
			if (success) {
				return ResponseEntity.ok("Password updated successfully.");
			} else {
				return ResponseEntity
				.badRequest()
				.body("Password update failed. Incorrect current password.");
			}
		} catch (Exception e) {
			LOGGER.error("Password change failed: {}", e.getMessage());
			return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password change failed.");
		}
	}

}
