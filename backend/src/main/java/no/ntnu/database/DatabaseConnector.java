package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import no.ntnu.database.sqlfunction.SqlConsumer;
import no.ntnu.database.sqlfunction.SqlFunction;

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
	 * If no result hook is provided, this method will return {@code null}.
	 *
	 * @param sqlQuery   The query to execute
	 * @param resultHook Function to handle the result, before it is closed
	 * @param <T>        The return type of the result hook
	 * @return The result from the query
	 * @throws SQLException If an exception occurs when sending the query
	 * @see #executeQuery(Query)
	 */
	public <T> T executeQuery(Query sqlQuery, SqlFunction<ResultSet, T> resultHook)
			throws SQLException {
		try (Statement connector = connection.createStatement()) {
			T result = null;
			if (resultHook != null) {
				result = resultHook.apply(connector.executeQuery(sqlQuery.getString()));
			}
			return result;
		}
	}

	/**
	 * Executes an SQL query.
	 *
	 * @param sqlQuery The query to execute
	 * @throws SQLException If an exception occurs when sending the query
	 * @see #executeQuery(Query, SqlFunction)
	 */
	public void executeQuery(Query sqlQuery) throws SQLException {
		executeQuery(sqlQuery, (SqlFunction<ResultSet, Object>) null);
	}

	/**
	 * Executes an SQL query with parameters, and returns the result.
	 * If no result hook is provided, or the statement doesn't return anything,
	 * this method will return {@code null}.
	 *
	 * @param sqlQuery    The query to execute
	 * @param prepareHook Consumer that allows assigning query parameters
	 * @param resultHook  Function to handle the result, before it is closed
	 * @param <T>         The return type of the result hook
	 * @return The result from the query
	 * @throws SQLException If an exception occurs when sending the query
	 */
	public <T> T executeQuery(
			Query sqlQuery,
			SqlConsumer<PreparedStatement> prepareHook,
			SqlFunction<ResultSet, T> resultHook
	) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sqlQuery.getString());
		prepareHook.accept(statement);
		T result = null;
		if (resultHook != null && statement.execute()) {
			result = resultHook.apply(statement.getResultSet());
		}
		return result;
	}

	/**
	 * Executes an SQL query with parameters.
	 *
	 * @param sqlQuery    The query to execute
	 * @param prepareHook Consumer that allows assigning query parameters
	 * @throws SQLException If an exception occurs when sending the query
	 */
	public <T> T executeQuery(String sql, SqlConsumer<PreparedStatement> prepareStatement, SqlFunction<ResultSet, T> handleResultSet) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareStatement.accept(statement);
			try (ResultSet resultSet = statement.executeQuery()) {
				return handleResultSet.apply(resultSet);
			}
		}
	}

	/**
	 * Executes an update (INSERT, UPDATE, DELETE) on the database.
	 *
	 * @param sql The SQL statement to execute as a String.
	 * @param prepareHook A consumer that sets the parameters of the PreparedStatement.
	 * @return The number of rows affected by the execution.
	 * @throws SQLException If an error occurs while executing the statement.
	 */
	public int executeUpdate(String sql, SqlConsumer<PreparedStatement> prepareHook) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			prepareHook.accept(statement);
			return statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}
	}
}
