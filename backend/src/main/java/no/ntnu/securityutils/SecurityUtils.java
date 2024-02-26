package no.ntnu.securityutils;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;

/**
 * Utility class providing security-related functionalities such as generating secrets for two-factor authentication.
 */
public class SecurityUtils {

	/**
	 * Generates a secret key for two-factor authentication (2FA) using a secure random number generator
	 * and encodes it in Base32 format. The generated key is suitable for use with TOTP (Time-Based One-Time Password) algorithms.
	 *
	 * The method ensures the secret key is free of any padding characters  that might be added during Base32 encoding.
	 *
	 * @return A Base32 encoded string representing the 2FA secret key.
	 */

	public static String generate2FASecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[10];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		String secretKey = base32.encodeToString(bytes).replace("=", "");
		return secretKey;
	}
}
