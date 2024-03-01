package no.ntnu.security;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;
import org.jboss.aerogear.security.otp.Totp;

/**
 * Utility class providing security-related functionalities,
 * such as generating secrets for two-factor authentication.
 */
public class SecurityUtil {

	private SecurityUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * <p>Generates a secret key for two-factor authentication (2FA) using
	 * a secure random number generator and encodes it in Base32 format.
	 * The generated key is suitable for use with TOTP (Time-Based One-Time Password) algorithms.
	 * </p><p>The method ensures the secret key is free of any padding characters
	 * that might be added during Base32 encoding.</p>
	 *
	 * @return A Base32 encoded string representing the 2FA secret key.
	 */

	public static String generate2FaSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[10];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes).replace("=", "");
	}

	/**
	 * Generates a QR code URL for a user's 2FA setup using their secret key.
	 *
	 * @param username The username of the user.
	 * @param secretKey The 2FA secret key of the user.
	 * @return The URL for the QR code to scan.
	 */
	public static String getQrCodeUrl(String username, String secretKey) {
		String issuer = "web-app-group-15";
		return String.format(
				"otpauth://totp/%s?secret=%s&issuer=%s",
				username,
				secretKey,
				issuer
		);
	}

	/**
	 * Verifies a 2FA token against a user's secret key.
	 *
	 * @param token The 2FA token to verify.
	 * @param secretKey The 2FA secret key of the user.
	 * @return True if the token is valid, false otherwise.
	 */
	public static boolean verify2FaToken(String token, String secretKey) {
		Totp totp = new Totp(secretKey);
		return totp.verify(token);
	}
}
