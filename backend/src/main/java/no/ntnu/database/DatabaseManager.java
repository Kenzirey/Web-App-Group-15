package no.ntnu.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Manager for the database, serves as an intermediary between the API & the database.
 */
public class DatabaseManager {
	private static DatabaseManager instance;

	private final DatabaseConnector connector;

	/**
	 * Creates the database manager.
	 *
	 * @param url      The url of the database to connect to
	 * @param username The username of the user to log in as
	 * @param password The password of the user to long in as
	 * @throws ClassNotFoundException If MySQL server drivers are not installed on this device
	 * @throws SQLException           If a connection to a database could not be
	 *                                established with the given credentials
	 */
	private DatabaseManager(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		this.connector = new DatabaseConnector(url, username, password);
	}

	/**
	 * Singleton. Needs to be manually initialized.
	 *
	 * @return Singleton instance
	 * @throws IllegalStateException If the database manager has not been initialized yet
	 * @see #initializeManager(String, String, String)
	 */
	public static synchronized DatabaseManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException("The database manager has not been initialized yet");
		}
		return instance;
	}

	/**
	 * Initializes the database manager. Should only be called once.
	 *
	 * @param dbUrl      The database url
	 * @param dbUsername The database username
	 * @param dbPassword The database password
	 * @throws ClassNotFoundException If MySQL server drivers are not installed on this device
	 * @throws SQLException           If a connection to a database could not be
	 * @throws IllegalStateException  If the database manager has already been initialized
	 */
	public static synchronized void initializeManager(
			String dbUrl, String dbUsername, String dbPassword
	) throws ClassNotFoundException, SQLException {
		if (instance != null) {
			throw new IllegalStateException("The database manager has already been initialized");
		}
		instance = new DatabaseManager(dbUrl, dbUsername, dbPassword);
	}

	/**
	 * Gets all categories.
	 *
	 * @return All categories
	 * @throws SQLException If an exception occurs when sending the SQL query
	 */
	public List<Map<String, String>> getAllCategories() throws SQLException {
		return connector.executeQuery(
				Query.SELECT_CATEGORY_ALL,
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Searches for a specific category.
	 *
	 * @param category   The search query to use when searching for categories
	 * @return Any categories that match the search query
	 * @throws SQLException If an exception occurs when sending the SQL query
	 */
	public List<Map<String, String>> searchCategory(String category) throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_CATEGORY,
				statement -> statement.setString(1, String.format("%%%s%%", category)),
				ResultFormatUtil::formatResultAs2dArray
		);
	}
}
