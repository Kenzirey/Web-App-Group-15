package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A template for connecting to a database, and running queries via Strings.
 * Primarily used for testing a simple SELECT query.
 */
public class JdbcTest {
	//Connection => Statement => ResultSet.

	private static final Logger LOGGER = Logger.getLogger(JdbcTest.class.getName());

	/**
	 * Main method. Application should contain 3-5 args when launched:
	 * <ol>
	 *     <li><b>Schema name:</b> The name of the schema within the local database to use</li>
	 *     <li><b>Username:</b> The username of your local database</li>
	 *     <li><b>Password:</b> The password of your local database</li>
	 *     <li><b>Port (optional):</b>
	 *     The port that the local database exists on. <i>Default: 3306</i></li>
	 *     <li><b>Address (optional):</b> The IP address of the database to connect to
	 *     <i>Default: 127.0.0.1 (localhost)</i></li>
	 * </ol>
	 * Any subsequent argument will be ignored
	 *
	 * @param args Program arguments
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 3) {
			throw new IllegalArgumentException("Application should be launched with 3-5 arguments: "
					+ "1: Schema name | 2: Username | 3: Password | "
					+ "4: Port (optional) | 5: Address (optional)"
			);
		}

		String url = parseUrl(args);
		String userName = args[1];
		String password = args[2];

		//Check if the driver import works.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Driver not found", e);
		}

		//The query via String format.
		String query = "SELECT * FROM category";

		//Create a connection to the database
		// () auto closes at end of catch, when used with try.
		try (
				Connection connection = DriverManager.getConnection(
						url, userName, password
				);
				Statement statement = connection.createStatement()
		) {

			//https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html
			//table of data representing the result set from a database query.
			ResultSet result = statement.executeQuery(query);
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();

			List<String> formattedEntries = new ArrayList<>();

			int entryNumber = 0;
			while (result.next()) {
				entryNumber++;

				List<String> formattedColumns = new ArrayList<>();
				for (int i = 1; i <= columnCount; i++) {
					formattedColumns.add(String.format("%s=%s",
							metaData.getColumnName(i),
							result.getString(i)
					));
				}
				formattedEntries.add(String.format("Entry %s: %s",
						entryNumber,
						String.join(" | ", formattedColumns)
				));

				//Increment entry number as we want the next entry to be 'entry + 1'.
			}
			LOGGER.log(Level.INFO,
					"Query returned {0} rows:\n{1}",
					new Object[]{entryNumber, String.join("\n", formattedEntries)}
			);

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Error reading query ", e);
		}
	}

	/**
	 * Parses the database URL.
	 *
	 * @param args The arguments provided in {@link #main(String[])}
	 * @return The parsed database URL
	 */
	private static String parseUrl(String[] args) {
		int port = 3306;
		if (args.length >= 4) {
			try {
				port = Integer.parseInt(args[3]);
				if (port < 0 || port > 65535) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("4th argument (port) is not a valid port "
						+ "(valid port numbers are 0 - 65535)");
			}
		}

		String address = "localhost";
		if (args.length >= 5) {
			address = args[4];
		}
		//test is the name of the 'test' database.
		return String.format("jdbc:mysql://%s:%s/%s", address, port, args[0]);
	}
}
