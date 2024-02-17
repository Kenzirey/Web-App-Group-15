package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A connector for connecting to a database, and safely sending queries to it.
 */
public class DatabaseConnector {
	private final Connection connection;

	/**
	 * Creates a database connection.
	 *
	 * @param url      The url of the database to connect to
	 * @param username The username of the user to log in as
	 * @param password The password of the user to long in as
	 * @throws ClassNotFoundException If MySQL server drivers are not installed on this device
	 * @throws SQLException           If a connection to a database could not be
	 *                                established with the given credentials
	 */
	public DatabaseConnector(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);
	}

	/**
	 * Executes an SQL query, and returns the result.
	 *
	 * @param sqlQuery   The query to execute
	 * @param resultHook Function to handle the result, before it is closed
	 * @param <T>        The return type of the result hook
	 * @return The result from the query
	 * @throws SQLException If an exception occurs when sending the query
	 * @see #executeQuery(Query)
	 */
	public <T> T executeQuery(Query sqlQuery, ResultFormatUtil.SqlFunction<ResultSet, T> resultHook)
			throws SQLException {
		try (Statement connector = connection.createStatement()) {
			return resultHook.apply(connector.executeQuery(sqlQuery.getString()));
		}
	}

	/**
	 * Executes an SQL query.
	 *
	 * @param sqlQuery The query to execute
	 * @throws SQLException If an exception occurs when sending the query
	 * @see #executeQuery(Query, ResultFormatUtil.SqlFunction)
	 */
	public void executeQuery(Query sqlQuery) throws SQLException {
		// Use this method to execute a query when you don't care about the result
		try (Statement connector = connection.createStatement()) {
			connector.executeQuery(sqlQuery.getString());
		}
	}
}
