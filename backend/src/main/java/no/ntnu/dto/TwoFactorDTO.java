package no.ntnu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for Two-Factor Authentication (2FA) operations.
 */
public class TwoFactorDTO {

	@Schema(description = "The 2FA token used for verification.")
	private String token;

	public TwoFactorDTO() {}

	public TwoFactorDTO(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
