package no.ntnu.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import no.ntnu.dto.TwoFactorDTO;
import no.ntnu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests, including setup and validation of two-factor authentication (2FA).
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(
		AuthenticationController.class
	);

	@Autowired
	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Sets up two-factor authentication for the user by generating a secret key and returning a QR code URL.
	 *
	 * @param principal the authenticated user
	 * @return ResponseEntity containing the QR code URL
	 */
	@Operation(
		summary = "Setup 2FA",
		description = "Generates a 2FA secret key and returns a QR code URL for the user."
	)
	@PostMapping("/setup-2fa")
	public ResponseEntity<?> setupTwoFactorAuthentication(Principal principal) {
		String secretKey = userService.generate2FASecretKey(principal.getName());
		String qrCodeUrl = userService.getQRCodeUrl(principal.getName(), secretKey);

		return ResponseEntity.ok(qrCodeUrl);
	}

	/**
	 * Verifies the 2FA setup by checking the provided token against the user's secret key.
	 *
	 * @param twoFactorDTO DTO containing the 2FA token
	 * @param principal the authenticated user
	 * @return ResponseEntity indicating the verification result
	 */
	@PostMapping("/verify-2fa")
	@Operation(
		summary = "Verify 2FA Setup",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "2FA Token verified successfully"
				),
			@ApiResponse(responseCode = "400", description = "Invalid 2FA Token"),
			@ApiResponse(responseCode = "500", description = "Server error"),
		}
	)
	public ResponseEntity<?> verifyTwoFactorSetup(
		@RequestBody TwoFactorDTO twoFactorDTO,
		Principal principal
	) {
		try {
			String secretKey = userService.get2FASecretKey(principal.getName());
			boolean isTokenValid = userService.verify2FAToken(
				twoFactorDTO.getToken(),
				secretKey
			);

			if (isTokenValid) {
				logger.info(
					"2FA token verified successfully for user: {}",
					principal.getName()
				);
				return ResponseEntity.ok("Two-Factor Authentication is enabled.");
			} else {
				logger.warn(
					"Invalid 2FA token provided by user: {}",
					principal.getName()
				);
				return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Invalid 2FA Token.");
			}
		} catch (Exception e) {
			logger.error(
				"Error during 2FA verification for user: {}",
				principal.getName(),
				e
			);
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An error occurred.");
		}
	}

	/**
	 * Validates a 2FA token for a user.
	 *
	 * @param twoFactorDTO DTO containing the 2FA token
	 * @param principal the authenticated user
	 * @return ResponseEntity indicating the validation result
	 */
	@PostMapping("/validate-2fa")
	@Operation(
		summary = "Validate 2FA Token",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Token validated, access granted"
				),
			@ApiResponse(responseCode = "401", description = "Invalid 2FA Token"),
			@ApiResponse(responseCode = "500", description = "Server error"),
		}
	)
	public ResponseEntity<?> validateTwoFactorToken(
		@RequestBody TwoFactorDTO twoFactorDTO,
		Principal principal
	) {
		try {
			String secretKey = userService.get2FASecretKey(principal.getName());
			boolean isTokenValid = userService.verify2FAToken(
				twoFactorDTO.getToken(),
				secretKey
			);

			if (isTokenValid) {
				logger.info(
					"2FA token validation successful for user: {}",
					principal.getName()
				);
				return ResponseEntity.ok("Token validated, access granted.");
			} else {
				logger.warn(
					"Invalid 2FA token attempt for user: {}",
					principal.getName()
				);
				return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid 2FA Token.");
			}
		} catch (Exception e) {
			logger.error(
				"Error during 2FA token validation for user: {}",
				principal.getName(),
				e
			);
			return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An error occurred.");
		}
	}
}
