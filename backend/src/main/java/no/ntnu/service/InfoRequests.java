package no.ntnu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import no.ntnu.database.DbConnectionWrapper;
import no.ntnu.database.Query;
import no.ntnu.database.ResultFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contains various database requests for retrieving information.
 */
@Component("infoRequests")
public class InfoRequests {
	private static final String SEARCH_QUERY_BASE = "%%%s%%";

	private final Connection connection;

	/**
	 * Makes the object for sending requests.
	 *
	 * @param dbConnectionWrapper Autowired wrapper object containing a database connection
	 */
	@Autowired
	public InfoRequests(DbConnectionWrapper dbConnectionWrapper) {
		this.connection = dbConnectionWrapper.getConnection();
	}

	/**
	 * Gets all categories.
	 *
	 * @return All categories
	 * @throws SQLException If an exception occurs when sending the SQL query
	 */
	public List<Map<String, String>> getAllCategories() throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SELECT_CATEGORY_ALL.getString()
		)) {
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
	}

	/**
	 * Searches for a specific category.
	 *
	 * @param category The search query to use when searching for categories
	 * @return Any categories that match the search query
	 * @throws SQLException If an exception occurs when sending the SQL query
	 */
	public List<Map<String, String>> searchCategory(String category) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SEARCH_FOR_CATEGORY.getString()
		)) {
			statement.setString(1, String.format(SEARCH_QUERY_BASE, category));
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
	}

	/**
	 * Gets all course providers.
	 *
	 * @return returns all course providers.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> getAllCourseProviders() throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SELECT_COURSE_PROVIDER_ALL.getString()
		)) {
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
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
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SEARCH_FOR_COURSE_PROVIDER.getString()
		)) {
			statement.setString(1, String.format(SEARCH_QUERY_BASE, courseProvider));
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
	}

	/**
	 * Returns all courses.
	 *
	 * @return returns all courses.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> getAllCourses() throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SELECT_PRODUCT_ALL.getString()
		)) {
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
	}

	/**
	 * Searches for a specific product (course).
	 *
	 * @param course The search query to use when searching for products (courses).
	 * @return returns any products (courses) that match the search query.
	 * @throws SQLException If an exception occurs when sending the SQL query.
	 */
	public List<Map<String, String>> searchCourse(String course) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.SEARCH_FOR_PRODUCT.getString()
		)) {
			statement.setString(1, String.format(SEARCH_QUERY_BASE, course));
			return ResultFormatUtil.formatResultAs2dArray(statement.executeQuery());
		}
	}

}
