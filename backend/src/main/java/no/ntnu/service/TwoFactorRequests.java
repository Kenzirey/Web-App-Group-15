package no.ntnu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.database.DbConnectionWrapper;
import no.ntnu.database.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contains various database requests for two factor operations.
 */
//@Component("twoFactorRequests")
public class TwoFactorRequests {
	private final Connection connection;

	/**
	 * Makes the object for sending requests.
	 *
	 * @param dbConnectionWrapper Autowired wrapper object containing a database connection
	 */
	//@Autowired
	public TwoFactorRequests(DbConnectionWrapper dbConnectionWrapper) {
		this.connection = dbConnectionWrapper.getConnection();
	}

	/**
	 * Retrieves the 2FA secret key for a user from the database.
	 *
	 * @param username The username for which the 2FA secret key is to be retrieved.
	 * @return The retrieved secret key or null if not found.
	 * @throws SQLException If an error occurs during the database query operation.
	 */
	public String get2FaSecretKey(String username) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.GET_2FA_SECRET.getString()
		)) {
			statement.setString(1, username);
			String twoFaSecret = null;
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				twoFaSecret = resultSet.getString("two_factor_secret");
			}
			return twoFaSecret;
		}
	}


	/**
	 * Updates the 2FA enabled status for a user in the database.
	 *
	 * @param username  The username for which the 2FA status is to be updated.
	 * @param isEnabled The new 2FA enabled status to be set.
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public void set2FaEnabled(String username, boolean isEnabled) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SET_2FA_ENABLED.getString()
		)) {
			statement.setBoolean(1, isEnabled);
			statement.setString(2, username);
			statement.executeUpdate();
		}
	}


	/**
	 * Retrieves the 2FA enabled status for a user from the database.
	 *
	 * @param username The username for which the 2FA status is to be checked.
	 * @return The 2FA enabled status.
	 * @throws SQLException If an error occurs during the database query operation.
	 */
	public boolean get2FaEnabled(String username) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.GET_2FA_ENABLED.getString()
		)) {
			statement.setString(1, username);
			boolean twoFaEnabled = false;
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				twoFaEnabled = resultSet.getBoolean("is_2fa_enabled");
			}
			return twoFaEnabled;
		}
	}

	/**
	 * Stores the 2FA secret key for a user in the database.
	 *
	 * @param username  The username for which the 2FA secret key is to be saved.
	 * @param secretKey The secret key to be stored.
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public void saveUser2FaSecretKey(String username, String secretKey) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.INSERT_2FA_SECRET.getString()
		)) {
			statement.setString(1, username);
			statement.setString(2, secretKey);
			statement.executeUpdate();
		}
	}
}
