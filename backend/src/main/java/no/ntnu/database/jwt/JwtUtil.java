package no.ntnu.database.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//TODO: Personalize it.
/**
 * Utility class for handling JWT tokens.
 * Taken from 05-jwt-authentication from app-dev repository by Gist.
 */
@Component
public class JwtUtil {
	@Value("${jwt_secret_key}")
	private String secretKey;
	/**
	 * Key inside JWT token where roles are stored.
	 */
	private static final String ROLE_KEY = "roles";
	private static final String NAME_KEY = "name";
	private static final String EMAIL_KEY = "email";
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


	/**
	 * Generate a JWT token for an authenticated user.
	 *
	 * @param userDetails Object containing user details
	 * @return JWT token string
	 */
	public String generateToken(UserDetails userDetails) {
		final long timeNow = System.currentTimeMillis();
		final long millisecondsInHour = 60 * 60 * 1000;
		final long timeAfterOneHour = timeNow + millisecondsInHour;

		List<String> roles = userDetails.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList());
		Long userId = null;
        if (userDetails instanceof AccessUserDetails) {
            userId = ((AccessUserDetails) userDetails).getUserId();
        }

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim(ROLE_KEY, roles)
				.claim(NAME_KEY, userDetails.getUsername())
				.claim(EMAIL_KEY, userDetails.getUsername())
				.claim("userId", userId)
				.issuedAt(new Date(timeNow))
				.expiration(new Date(timeAfterOneHour))
				.signWith(getSigningKey())
				.compact();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
	}

	/**
	 * Find username from a JWT token.
	 *
	 * @param token JWT token
	 * @return Username
	 */
	public String extractUsername(String token) throws JwtException {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Check if a token is valid for a given user.
	 *
	 * @param token       Token to validate
	 * @param userDetails Object containing user details
	 * @return True if the token matches the current user and is still valid
	 */
	public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
		final String username = extractUsername(token);
		return userDetails != null
				&& username.equals(userDetails.getUsername())
				&& !isTokenExpired(token);
	}


	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey())
				.build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public JwtUtil() {
        logger.info("Loaded jwt_secret_key: {}", secretKey);
    }
}
