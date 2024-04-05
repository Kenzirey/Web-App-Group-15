package no.ntnu.jwt;

/**
 * Data that we will send as a response to the user when the authentication is successful.
 */
//TODO: Record?
public class AuthenticationResponse {
	private final String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
}
