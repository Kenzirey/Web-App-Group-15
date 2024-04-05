package no.ntnu.jwt;

import no.ntnu.jwt.AccessUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class HelloTest {

	/**
	 * Handle HTTP GET /user request.
	 *
	 * @return The body to be returned in the HTTP response
	 */
	@GetMapping("user")
	public String userPage(@AuthenticationPrincipal AccessUserDetails loggedInUser) {
		return "You are currently logged in as " + loggedInUser.getUsername();
	}
	/**
	 * Handle HTTP GET /admin request.
	 *
	 * @return The body to be returned in the HTTP response
	 */
	@GetMapping("admin")
	public String adminPage() {
		// Here we use another way to get reference to currently logged-in user
		Authentication auth = getAuthenticatedUser();
		String username = auth.getName();
		String roleString = String.join(", ", getRoles(auth));
		return "You are logged in as " + username + ". You have the following roles: " + roleString;
	}

	private static Authentication getAuthenticatedUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private static List<String> getRoles(Authentication auth) {
		return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
	}
}
