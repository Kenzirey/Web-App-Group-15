package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Wrapper class for a database connection.
 * Responsible for discreetly delivering the connection to any class doing SQL requests.
 */
@Component("dbConnectionWrapper")
public class DbConnectionWrapper {
	private static Connection connection;

	/**
	 * Initializes the database connection. Should only be called once.
	 *
	 * @param dbUrl      The database url
	 * @param dbUsername The database username
	 * @param dbPassword The database password
	 * @throws ClassNotFoundException If MySQL server drivers are not installed on this device
	 * @throws SQLException           If a connection to a database could not be initialized
	 * @throws IllegalStateException  If the database connection has already been initialized
	 */
	public static synchronized void initializeConnection(
			String dbUrl, String dbUsername, String dbPassword
	) throws ClassNotFoundException, SQLException {
		if (connection != null) {
			throw new IllegalStateException("The database connection has already been initialized");
		}
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	}

	/**
	 * Gets the connection wrapped in this wrapper class.
	 * This connection will always be the same, and is initialized using
	 * {@link #initializeConnection(String, String, String)}.
	 *
	 * @return The wrapped connection
	 */
	public Connection getConnection() {
		if (connection == null) {
			throw new IllegalStateException("The database connection has not been initialized yet");
		}
		return connection;
	}
}
