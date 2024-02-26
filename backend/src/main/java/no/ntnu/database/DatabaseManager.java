package no.ntnu.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import no.ntnu.database.sqlfunction.SqlConsumer;
import no.ntnu.database.sqlfunction.SqlFunction;

/**
 * Manager for the database, serves as an intermediary between the API & the database.
 */
public class DatabaseManager {
	private static DatabaseManager instance;

	private final DatabaseConnector connector;
	//TODO: rename this to a more fitting constant name. Henrik, suggestions?
	private static final String WIZARDRY = "%%%s%%";

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
				statement -> statement.setString(1, String.format(WIZARDRY, category)),
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Gets all course providers.
	 *
	 * @return returns all course providers.
	 *
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
	 *
	 * @return Any course providers that match the search query.
	 *
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> searchCourseProvider(String courseProvider)
			throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_COURSE_PROVIDER,
				statement -> statement.setString(1, String.format(WIZARDRY, courseProvider)),
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
	 *
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
	 *
	 * @return returns any products (courses) that match the search query.
	 *
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public  List<Map<String, String>> searchProduct(String product) throws SQLException {
		return connector.executeQuery(
				Query.SEARCH_FOR_PRODUCT,
				statement -> statement.setString(1, String.format(WIZARDRY, product)),
				ResultFormatUtil::formatResultAs2dArray
		);
	}

	/**
	 * Executes a database update operation such as INSERT, UPDATE, or DELETE.
	 *
	 * @param query The enumeration of the SQL query to be executed.
	 * @param prepareHook A functional interface to set the parameters of the PreparedStatement.
	 * @return The number of rows affected by the operation.
	 * @throws SQLException If an error occurs during the database update execution.
	 */
	public int executeUpdate(Query query, SqlConsumer<PreparedStatement> prepareHook) throws SQLException {

		String sql = query.getString();
		return connector.executeUpdate(sql, prepareHook);
	}
	
	

	/**
	 * Stores the 2FA secret key for a user in the database.
	 *
	 * @param username The username for which the 2FA secret key is to be saved.
	 * @param secretKey The secret key to be stored.
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public void saveUser2FASecretKey(String username, String secretKey) throws SQLException {
		String sql = Query.INSERT_2FA_SECRET.getString();
		executeUpdate(sql, statement -> {
			statement.setString(1, username);
			statement.setString(2, secretKey);
		});
	}
	
	/**
	 * Retrieves the 2FA secret key for a user from the database.
	 *
	 * @param username The username for which the 2FA secret key is to be retrieved.
	 * @return The retrieved secret key or null if not found.
	 * @throws SQLException If an error occurs during the database query operation.
	 */

	public String get2FASecretKey(String username) throws SQLException {
		String sql = Query.GET_2FA_SECRET.getString();
		return executeQuery(sql, statement -> statement.setString(1, username), resultSet -> {
			if (resultSet.next()) {
				return resultSet.getString("two_factor_secret");
			}
			return null;
		});
	}

		
	/**
	 * Updates the 2FA enabled status for a user in the database.
	 *
	 * @param username The username for which the 2FA status is to be updated.
	 * @param isEnabled The new 2FA enabled status to be set.
	 * @throws SQLException If an error occurs during the database update operation.
	 */

	public void set2FAEnabled(String username, boolean isEnabled) throws SQLException {
		String sql = Query.SET_2FA_ENABLED.getString();
		executeUpdate(sql, statement -> {
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
	public boolean get2FAEnabled(String username) throws SQLException {
		String sql = Query.GET_2FA_ENABLED.getString();
		return executeQuery(sql, statement -> statement.setString(1, username), resultSet -> {
			if (resultSet.next()) {
				return resultSet.getBoolean("is_2fa_enabled");
			}
			return false;
		});
	}

	/**
	 * Creates the 2FA table in the database if it does not exist.
	 *
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public void create2FATable() throws SQLException {
		String sql = Query.CREATE_USERS_2FA_TABLE.getString();
		executeUpdate(sql, PreparedStatement::execute);
	}


	/**
	 * Executes an update operation with the given SQL string.
	 *
	 * @param sql The SQL statement to execute as a String.
	 * @param prepareStatement A consumer that sets the parameters of the PreparedStatement.
	 * @return The number of rows affected by the execution.
	 * @throws SQLException If an error occurs while executing the statement.
	 */
	public int executeUpdate(String sql, SqlConsumer<PreparedStatement> prepareStatement) throws SQLException {

		return this.connector.executeUpdate(sql, prepareStatement);
	}
	

	/**
	 * Executes a query operation with the given SQL string.
	 *
	 * @param sql The SQL query to execute as a String.
	 * @param prepareStatement A consumer that sets the parameters of the PreparedStatement.
	 * @param handleResultSet A function to process the ResultSet.
	 * @param <T> The return type of the result processing function.
	 * @return The processed result.
	 * @throws SQLException If an error occurs while executing the query.
	 */
	public <T> T executeQuery(String sql, SqlConsumer<PreparedStatement> prepareStatement, SqlFunction<ResultSet, T> handleResultSet) throws SQLException {
		return this.connector.executeQuery(sql, prepareStatement, handleResultSet);
	}
}
