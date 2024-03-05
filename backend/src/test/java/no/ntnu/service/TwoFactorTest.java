package no.ntnu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

class TwoFactorTest {

	@Mock
	private TwoFactorRequests twoFactor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void saveUser2FASecretKey() throws SQLException {
		String username = "testuser";
		String secretKey = "secret123";

		doNothing().when(twoFactor).saveUser2FaSecretKey(username, secretKey);

		twoFactor.saveUser2FaSecretKey(username, secretKey);

		verify(twoFactor, times(1)).saveUser2FaSecretKey(username, secretKey);
	}

	@Test
	void get2FASecretKey() throws SQLException {
		String username = "testuser";
		String expectedSecretKey = "secret123";

		when(twoFactor.get2FaSecretKey(username)).thenReturn(expectedSecretKey);

		String secretKey = twoFactor.get2FaSecretKey(username);

		verify(twoFactor, times(1)).get2FaSecretKey(username);
		assertEquals(expectedSecretKey, secretKey);
	}

	@Test
	void set2FAEnabled() throws SQLException {
		String username = "testuser";
		boolean isEnabled = true;

		doNothing().when(twoFactor).set2FaEnabled(username, isEnabled);

		twoFactor.set2FaEnabled(username, isEnabled);

		verify(twoFactor, times(1)).set2FaEnabled(username, isEnabled);
	}

	@Test
	void get2FAEnabled() throws SQLException {
		String username = "testuser";
		boolean expectedEnabledStatus = true;

		when(twoFactor.get2FaEnabled(username)).thenReturn(expectedEnabledStatus);

		boolean isEnabled = twoFactor.get2FaEnabled(username);

		verify(twoFactor, times(1)).get2FaEnabled(username);
		assertEquals(expectedEnabledStatus, isEnabled);
	}
}
