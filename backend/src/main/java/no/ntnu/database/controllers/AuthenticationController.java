package no.ntnu.database.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import no.ntnu.dto.TwoFactorDto;
import no.ntnu.security.SecurityUtil;
import no.ntnu.service.TwoFactorRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests, including setup and validation
 * of two-factor authentication (2FA).
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final TwoFactorRequests twoFactorRequests;

    @Autowired
    public AuthenticationController(TwoFactorRequests twoFactorRequests) {
        this.twoFactorRequests = twoFactorRequests;
    }

    /**
     * Sets up two-factor authentication by generating a secret key and QR code URL.
     *
     * @param principal the authenticated user
     * @return ResponseEntity containing the QR code URL
     */
    @PostMapping("/setup-2fa")
    @Operation(summary = "Setup 2FA",
	 	description = "Generates a 2FA secret key and returns a QR code URL for the user.")
    public ResponseEntity<String> setupTwoFactorAuthentication(Principal principal) {
        String secretKey = SecurityUtil.generate2FaSecretKey();
        twoFactorRequests.saveUser2FaSecretKey(principal.getName(), secretKey);
        String qrUrl = SecurityUtil.getQrCodeUrl(principal.getName(), secretKey);
        LOGGER.info("2FA setup initiated for user: {}", principal.getName());
        return ResponseEntity.ok(qrUrl);
    }

    /**
     * Verifies the 2FA setup by checking the provided token against the users secret key.
     *
     * @param twoFactorDto DTO containing the 2FA token
     * @param principal    the authenticated user
     * @return ResponseEntity indicating the verification result
     */
    @PostMapping("/verify-2fa")
    @Operation(summary = "Verify 2FA Setup")
    @ApiResponse(responseCode = "200", description = "2FA Token verified successfully")
    @ApiResponse(responseCode = "400", description = "Invalid 2FA Token")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> verifyTwoFactorSetup(
            @RequestBody TwoFactorDto twoFactorDto, Principal principal) {
        return twoFactorRequests.get2FaSecretKey(principal.getName())
            .map(secretKey -> {
                if (SecurityUtil.verify2FaToken(twoFactorDto.getToken(), secretKey)) {
                    LOGGER.info("2FA token verified for user: {}", principal.getName());
                    return ResponseEntity.ok("Two-Factor Authentication is enabled.");
                } else {
                    LOGGER.warn("Invalid 2FA token provided by user: {}", principal.getName());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid 2FA Token.");
                }
            })
            .orElseGet(() -> {
                LOGGER.error("No 2FA secret key found for user: {}", principal.getName());
                return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("2FA setup not found.");
            });
    }

    /**
     * Validates a 2FA token to grant or deny access based on its validity.
     *
     * @param twoFactorDto DTO containing the 2FA token
     * @param principal    the authenticated user
     * @return ResponseEntity indicating the validation result
     */
    @PostMapping("/validate-2fa")
    @Operation(summary = "Validate 2FA Token")
    @ApiResponse(responseCode = "200", description = "Token validated, access granted")
    @ApiResponse(responseCode = "401", description = "Invalid 2FA Token")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> validateTwoFactorToken(
            @RequestBody TwoFactorDto twoFactorDto, Principal principal) {
        return twoFactorRequests.get2FaSecretKey(principal.getName())
            .map(secretKey -> {
               	if (SecurityUtil.verify2FaToken(twoFactorDto.getToken(), secretKey)) {
                 	LOGGER.info("2FA token validation successful for user: {}",
						principal.getName()
					);
                    return ResponseEntity.ok("Token validated, access granted.");
                } else {
                    LOGGER.warn("Invalid 2FA token attempt for user: {}", principal.getName());
                    return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Invalid 2FA Token.");
                }
            })
            .orElseGet(() -> {
                LOGGER.error("No 2FA secret key found for user: {}", principal.getName());
                return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("2FA setup not found.");
            });
    }
}
