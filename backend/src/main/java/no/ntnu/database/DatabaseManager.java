package no.ntnu.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import no.ntnu.dto.Course;

/**
 * Manager for the database, serves as an intermediary between the API & the database.
 */
public class DatabaseManager {
	private static DatabaseManager instance;

	private final DatabaseConnector connector;
	private static final String SEARCH_QUERY_BASE = "%%%s%%";

	/**
	 * Creates the database manager.
	 *
	 * @param url      The url of the database to connect to
	 * @param username The username of the user to log in as
	 * @param password The password of the user to log in as
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

	// INFO QUERY OPERATIONS:

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
	 * @param category The search query to use when searching for categories
	 * @return Any categories that match the search query
	 * @throws SQLException If an exception occurs when sending the SQL query
	 */
	public List<Map<String, String>> searchCategory(String category) throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_CATEGORY,
				statement -> statement.setString(
						1,
						String.format(SEARCH_QUERY_BASE, category)
				),
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Gets all course providers.
	 *
	 * @return returns all course providers.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> getAllCourseProviders() throws SQLException {
		return connector.executeQuery(
				Query.SELECT_COURSE_PROVIDER_ALL,
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Searches for a specific course provider.
	 *
	 * @param courseProvider The search query to use when searching for course providers.
	 * @return Any course providers that match the search query.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> searchCourseProvider(String courseProvider)
			throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_COURSE_PROVIDER,
				statement -> statement.setString(
						1,
						String.format(SEARCH_QUERY_BASE, courseProvider)
				),
				//This is the same as:
				//resultSet -> ResultFormatUtil.formatResultAs2dArray(resultSet)
				//method reference, where method's implementation is passed as an argument.
				//passes method as an argument to another method (executeQuery).
				// Indicating that formalResultAs2dArray will process the query's results.
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Gets all products (courses).
	 *
	 * @return returns all products (courses).
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> getAllProducts() throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_PRODUCT_ALL,
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Searches for a specific product (course).
	 *
	 * @param product The search query to use when searching for products (courses).
	 * @return returns any products (courses) that match the search query.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> searchProduct(String product) throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_PRODUCT,
				statement -> statement.setString(1, String.format(SEARCH_QUERY_BASE, product)),
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Stores the 2FA secret key for a user in the database.
	 *
	 * @param username  The username for which the 2FA secret key is to be saved.
	 * @param secretKey The secret key to be stored.
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public void saveUser2FaSecretKey(String username, String secretKey) throws SQLException {
		connector.executeUpdate(Query.INSERT_2FA_SECRET, statement -> {
			statement.setString(1, username);
			statement.setString(2, secretKey);
		});
	}

	// ADMIN CREATE / DELETE OPERATIONS

	/**
	 * <p>Inserts a new course into the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course already exists, {@code true} otherwise</p>
	 *
	 * @param course The course to add
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean insertCourse(
			Course course
	) throws SQLException {
		return connector.executeUpdate(
				Query.INSERT_COURSE,
				statement -> {
					statement.setString(1, course.getName());
					statement.setString(2, course.getDescription());
					statement.setInt(3, course.getCreatedBy());
				}
		) > 0;
	}

	/**
	 * <p>Removes an existing course from the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course does not exist, {@code true} otherwise</p>
	 *
	 * @param courseId The ID of the course to remove
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean removeCourse(int courseId) throws SQLException {
		return connector.executeUpdate(
				Query.DELETE_COURSE,
				statement -> statement.setInt(3, courseId)
		) > 0;
	}

	/**
	 * <p>Updates an existing course into the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course was already updated, {@code true} otherwise</p>
	 *
	 * @param courseId The ID of the course to update
	 * @param newCourse The new & updated course
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean updateCourse(
			int courseId,
			Course newCourse
	) throws SQLException {
		return connector.executeUpdate(
				Query.UPDATE_COURSE,
				statement -> {
					statement.setString(1, newCourse.getName());
					statement.setString(2, newCourse.getDescription());
					statement.setInt(3, courseId);
				}
		) > 0;
	}

	// 2FA OPERATIONS

	/**
	 * Retrieves the 2FA secret key for a user from the database.
	 *
	 * @param username The username for which the 2FA secret key is to be retrieved.
	 * @return The retrieved secret key or null if not found.
	 * @throws SQLException If an error occurs during the database query operation.
	 */

	public String get2FaSecretKey(String username) throws SQLException {
		return connector.executeQuery(
				Query.GET_2FA_SECRET,
				statement -> statement.setString(1, username),
				resultSet -> {
					String twoFaSecret = null;
					if (resultSet.next()) {
						twoFaSecret = resultSet.getString("two_factor_secret");
					}
					return twoFaSecret;
				});
	}


	/**
	 * Updates the 2FA enabled status for a user in the database.
	 *
	 * @param username  The username for which the 2FA status is to be updated.
	 * @param isEnabled The new 2FA enabled status to be set.
	 * @throws SQLException If an error occurs during the database update operation.
	 */

	public void set2FaEnabled(String username, boolean isEnabled) throws SQLException {
		connector.executeUpdate(Query.SET_2FA_ENABLED, statement -> {
			statement.setBoolean(1, isEnabled);
			statement.setString(2, username);
		});
	}


	/**
	 * Retrieves the 2FA enabled status for a user from the database.
	 *
	 * @param username The username for which the 2FA status is to be checked.
	 * @return The 2FA enabled status.
	 * @throws SQLException If an error occurs during the database query operation.
	 */
	public boolean get2FaEnabled(String username) throws SQLException {
		return connector.executeQuery(
				Query.GET_2FA_ENABLED,
				statement -> statement.setString(1, username),
				resultSet -> {
					boolean twoFaEnabled = false;
					if (resultSet.next()) {
						twoFaEnabled = resultSet.getBoolean("is_2fa_enabled");
					}
					return twoFaEnabled;
				}
		);
	}

	/**
	 * Creates the 2FA table in the database if it does not exist.
	 *
	 * @throws SQLException If an error occurs during the database update operation.
	 * @deprecated Table should already exist, creating it should be done in advance in sql script
	 */
	@Deprecated(since = "1.0.0-SNAPSHOT", forRemoval = true)
	public void create2FaTable() throws SQLException {
		connector.executeUpdate(Query.CREATE_USERS_2FA_TABLE, null);
	}
}
