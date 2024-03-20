package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.dto.TwoFactorDto;
import no.ntnu.security.SecurityUtil;
import no.ntnu.service.TwoFactorRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests,
 * including setup and validation of two-factor authentication (2FA).
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

	private final TwoFactorRequests requests;

	/**
	 * Creates the controller.
	 *
	 * @param twoFactorRequests Autowired object for sending requests to the database
	 */
	@Autowired
	public AuthenticationController(TwoFactorRequests twoFactorRequests) {
		this.requests = twoFactorRequests;
	}


	/**
	 * Sets up two-factor authentication for the user by
	 * generating a secret key and returning a QR code URL.
	 *
	 * @param principal the authenticated user
	 * @return ResponseEntity containing the QR code URL
	 */
	@Operation(
			summary = "Setup 2FA",
			description = "Generates a 2FA secret key and returns a QR code URL for the user."
	)
	@PostMapping("/setup-2fa")
	public ResponseEntity<String> setupTwoFactorAuthentication(Principal principal) {
		ResponseEntity<String> response;
		try {
			String secretKey = SecurityUtil.generate2FaSecretKey();
			requests.saveUser2FaSecretKey(principal.getName(), secretKey);
			response = ResponseEntity.ok(
					SecurityUtil.getQrCodeUrl(principal.getName(), secretKey)
			);
		} catch (SQLException sqle) {
			response = ResponseEntity.internalServerError().build();
		}
		return response;
	}

	/**
	 * Verifies the 2FA setup by checking the provided token against the user's secret key.
	 *
	 * @param twoFactorDto DTO containing the 2FA token
	 * @param principal    the authenticated user
	 * @return ResponseEntity indicating the verification result
	 */
	@PostMapping("/verify-2fa")
	@Operation(
			summary = "Verify 2FA Setup"
	)
	@ApiResponse(
			responseCode = "200",
			description = "2FA Token verified successfully"
	)
	@ApiResponse(responseCode = "400", description = "Invalid 2FA Token")
	@ApiResponse(responseCode = "500", description = "Server error")
	public ResponseEntity<String> verifyTwoFactorSetup(
			@RequestBody TwoFactorDto twoFactorDto,
			Principal principal
	) {
		try {
			String secretKey = requests.get2FaSecretKey(principal.getName());
			boolean isTokenValid = SecurityUtil.verify2FaToken(
					twoFactorDto.getToken(),
					secretKey
			);

			if (isTokenValid) {
				logger.log(
						Level.INFO,
						"2FA token verified successfully for user: {}",
						principal.getName()
				);
				return ResponseEntity.ok("Two-Factor Authentication is enabled.");
			} else {
				logger.log(
						Level.WARNING,
						"Invalid 2FA token provided by user: {}",
						principal.getName()
				);
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("Invalid 2FA Token.");
			}
		} catch (Exception e) {
			logger.log(
					Level.WARNING,
					String.format(
							"Error during 2FA verification for user: %s",
							principal.getName()
					),
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
	 * @param twoFactorDto DTO containing the 2FA token
	 * @param principal    the authenticated user
	 * @return ResponseEntity indicating the validation result
	 */
	@PostMapping("/validate-2fa")
	@Operation(
			summary = "Validate 2FA Token"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Token validated, access granted"
	)
	@ApiResponse(responseCode = "401", description = "Invalid 2FA Token")
	@ApiResponse(responseCode = "500", description = "Server error")
	public ResponseEntity<String> validateTwoFactorToken(
			@RequestBody TwoFactorDto twoFactorDto,
			Principal principal
	) {
		try {
			String secretKey = requests.get2FaSecretKey(principal.getName());
			boolean isTokenValid = SecurityUtil.verify2FaToken(
					twoFactorDto.getToken(),
					secretKey
			);

			if (isTokenValid) {
				logger.log(
						Level.INFO,
						"2FA token validation successful for user: {}",
						principal.getName()
				);
				return ResponseEntity.ok("Token validated, access granted.");
			} else {
				logger.log(
						Level.WARNING,
						"Invalid 2FA token attempt for user: {}",
						principal.getName()
				);
				return ResponseEntity
						.status(HttpStatus.UNAUTHORIZED)
						.body("Invalid 2FA Token.");
			}
		} catch (Exception e) {
			logger.log(
					Level.WARNING,
					String.format(
							"Error during 2FA token validation for user: %s",
							principal.getName()
					),
					e
			);
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred.");
		}
	}
}
