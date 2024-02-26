package no.ntnu.service;

import no.ntnu.database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

class UserServiceTest {

	@Mock
	private DatabaseManager databaseManager;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void saveUser2FASecretKey() throws SQLException {
		String username = "testuser";
		String secretKey = "secret123";

		doNothing().when(databaseManager).saveUser2FaSecretKey(username, secretKey);

		userService.saveUser2FASecretKey(username, secretKey);

		verify(databaseManager, times(1)).saveUser2FaSecretKey(username, secretKey);
	}

	@Test
	void get2FASecretKey() throws SQLException {
		String username = "testuser";
		String expectedSecretKey = "secret123";

		when(databaseManager.get2FaSecretKey(username)).thenReturn(expectedSecretKey);

		String secretKey = userService.get2FASecretKey(username);

		verify(databaseManager, times(1)).get2FaSecretKey(username);
		assertEquals(expectedSecretKey, secretKey);
	}

	@Test
	void set2FAEnabled() throws SQLException {
		String username = "testuser";
		boolean isEnabled = true;

		doNothing().when(databaseManager).set2FaEnabled(username, isEnabled);

		userService.set2FAEnabled(username, isEnabled);

		verify(databaseManager, times(1)).set2FaEnabled(username, isEnabled);
	}

	@Test
	void get2FAEnabled() throws SQLException {
		String username = "testuser";
		boolean expectedEnabledStatus = true;

		when(databaseManager.get2FaEnabled(username)).thenReturn(expectedEnabledStatus);

		boolean isEnabled = userService.get2FAEnabled(username);

		verify(databaseManager, times(1)).get2FaEnabled(username);
		assertEquals(expectedEnabledStatus, isEnabled);
	}
}
