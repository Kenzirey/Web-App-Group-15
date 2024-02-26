package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	 * <p>Executes an SQL query with no dynamic parameters, and returns the result.
	 * If no result hook is provided, this method will return {@code null}.</p>
	 * <p>This method is equivalent of calling
	 * {@link #executeQuery(Query, SqlConsumer, SqlFunction)}
	 * with {@code executeQuery(sqlQuery, null, resultHook)}</p>
	 *
	 * @param sqlQuery   The query to execute
	 * @param resultHook Function to handle the result, before it is closed
	 * @param <T>        The return type of the result hook
	 * @return The result from the query
	 * @throws SQLException If an exception occurs when sending the query
	 * @see #executeQuery(Query, SqlConsumer, SqlFunction)
	 */
	public <T> T executeQuery(
			Query sqlQuery,
			SqlFunction<ResultSet, T> resultHook
	) throws SQLException {
		return executeQuery(sqlQuery, null, resultHook);
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
	 * @see #executeQuery(Query, SqlFunction)
	 */
	public <T> T executeQuery(
			Query sqlQuery,
			SqlConsumer<PreparedStatement> prepareHook,
			SqlFunction<ResultSet, T> resultHook
	) throws SQLException {
		T result = null;
		try (PreparedStatement statement = connection.prepareStatement(sqlQuery.getString())) {
			if (prepareHook != null) {
				prepareHook.accept(statement);
			}
			if (resultHook != null && statement.execute()) {
				result = resultHook.apply(statement.getResultSet());
			}
		}

		return result;
	}

	/**
	 * (TODO) Jonas: Don't use string as query, it's vulnerable to injections.
	 * <br/>(TODO) use a pre-defined Query (enum), and fill in parameters with a prepareHook
	 *
	 * @param sql              Don't pass a string here
	 * @param prepareStatement .
	 * @param handleResultSet  .
	 * @param <T>              .
	 * @return .
	 * @deprecated Use {@link #executeQuery(Query, SqlConsumer, SqlFunction)} instead.
	 */
	@Deprecated(since = "1.0.0-SNAPSHOT", forRemoval = true)
	public <T> T executeQuery(
			String sql,
			SqlConsumer<PreparedStatement> prepareStatement,
			SqlFunction<ResultSet, T> handleResultSet
	) throws SQLException {
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
	 * @param sql         The SQL statement to execute as a String.
	 * @param prepareHook A consumer that sets the parameters of the PreparedStatement.
	 * @return The number of rows affected by the execution.
	 * @throws SQLException If an error occurs while executing the statement.
	 */
	public int executeUpdate(
			Query sql,
			SqlConsumer<PreparedStatement> prepareHook
	) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(sql.getString())) {
			if (prepareHook != null) {
				prepareHook.accept(statement);
			}
			return statement.executeUpdate();
		}
	}
}
