package no.ntnu.database;


import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;


/**
 * Utility for running SQL scripts.
 */
public class ScriptUtility {
	//Script Utility template taken from:
	//https://www.baeldung.com/java-run-sql-script


	//https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/executing-sql.html

	private ScriptUtility() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * TODO: Someone made a javadoc-less methond, git blame Emma :).
	 *
	 * @param path TODO: document what this parameter is
	 * @param connection TODO: document what this parameter is
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
	 * Returns a datasource to the test database in MySQL.
	 *
	 * @return dataSource to the test database in MySQL.
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
