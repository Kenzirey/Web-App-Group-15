package no.ntnu.database;


import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;


/**
 * Utility for running SQL scripts on a database.
 * Contains methods to execute SQL scripts from a given path,
 * using a database connection.
 *
 * <p> Script execution supports custom comment delimiters, script statement separators,
 * as well as the option to continue
 * execution on error or to ignore failed drops.</p>
 */
public class ScriptUtility {
	//Script Utility template taken from:
	//https://www.baeldung.com/java-run-sql-script


	//https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/executing-sql.html

	private ScriptUtility() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Executes an SQL script located at the specified path using the provided {@link Connection}.
	 * This method allows the execution of SQL scripts with custom settings for handling comments,
	 * statement separators, and block comments.
	 *
	 * @param path       the file system path to the SQL script file.
	 * @param connection the {@link Connection} to the database where the script will be executed.
	 */
	public static void runScript(String path, Connection connection) {
		//To support multiple comment delimiters.
		boolean continueOrError = false;
		boolean ignoreFailedDrops = false;
		String commentPrefix = "--";
		String separator = ";";
		String blockCommentStartDelimiter = "/*";
		String blockCommentEndDelimiter = "*/";

		ScriptUtils.executeSqlScript(
				connection,
				new EncodedResource(new PathResource(path)),
				continueOrError,
				ignoreFailedDrops,
				commentPrefix,
				separator,
				blockCommentStartDelimiter,
				blockCommentEndDelimiter
		);
	}

	/**
	 * Creates and returns a datasource to a test database in MySQL (localhost).
	 * Configured via JDBC connection details for the test database.
	 *
	 * @return a {@link DataSource} configured dataSource for accessing the test database.
	 */
	public static DataSource getMySqlDataSourceTest() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setUsername("root");
		dataSource.setPassword("abcd1234");

		return dataSource;
	}


}
