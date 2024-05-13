package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import no.ntnu.database.services.UserService;
import no.ntnu.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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
}
