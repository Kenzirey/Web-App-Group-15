package no.ntnu;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ntnu.database.DbConnectionWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main back-end spring boot application.
 */
@SpringBootApplication
public class JavaBackendApplication {
	private static final Logger LOGGER = Logger.getLogger(JavaBackendApplication.class.getName());

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
		try {
			DbConnectionWrapper.initializeConnection(parseUrl(args), args[1], args[2]);
			SpringApplication.run(JavaBackendApplication.class, args);
		} catch (ClassNotFoundException cnfe) {
			LOGGER.log(
					Level.SEVERE,
					"MySQL server drivers are not installed on this device",
					cnfe
			);
		} catch (SQLException sqle) {
			LOGGER.log(
					Level.SEVERE,
					"A connection to a database could not be "
							+ "established with the given credentials",
					sqle
			);
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
