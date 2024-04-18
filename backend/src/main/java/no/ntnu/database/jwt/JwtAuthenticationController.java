package no.ntnu.database.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.security.SecurityUtil;

/**
 * Controller responsible for the JWT authentication. Work in progress,
 * until we integrate it with the regular AuthenticationController.
 */
@RestController
public class JwtAuthenticationController {

	//TODO: Fix Autowired and beans bug.
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;


	/**
	 * HTTP POST request to /authenticate.
	 *
	 * @param authenticationRequest The request JSON object containing username and password
	 * @return OK + JWT token; Or UNAUTHORIZED
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (userDetails instanceof AccessUserDetails) {
            AccessUserDetails customUserDetails = (AccessUserDetails) userDetails;
            if (customUserDetails.isTwoFactorEnabled()) {
                if (authenticationRequest.getTwoFactorToken() == null || 
                    !SecurityUtil.verify2FaToken(authenticationRequest.getTwoFactorToken(), customUserDetails.getTwoFactorSecret())) {
                    return new ResponseEntity<>("Invalid 2FA token", HttpStatus.UNAUTHORIZED);
                }
            }
        }
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
