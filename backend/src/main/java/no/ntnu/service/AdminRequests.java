package no.ntnu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import no.ntnu.database.DbConnectionWrapper;
import no.ntnu.database.Query;
import no.ntnu.dto.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contains various database requests for admin operations.
 */
//@Component("adminRequests")
public class AdminRequests {
	private final Connection connection;

	/**
	 * Makes the object for sending requests.
	 *
	 * @param dbConnectionWrapper Autowired wrapper object containing a database connection
	 */
	//@Autowired
	public AdminRequests(DbConnectionWrapper dbConnectionWrapper) {
		this.connection = dbConnectionWrapper.getConnection();
	}

	/**
	 * <p>Inserts a new course into the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course already exists, {@code true} otherwise</p>
	 *
	 * @param course The course to add
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean insertCourse(Course course) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.INSERT_COURSE.getString()
		)) {
			statement.setString(1, course.getName());
			statement.setString(2, course.getDescription());
			statement.setInt(3, course.getCreatedBy());
			return statement.executeUpdate() > 0;
		}
	}

	/**
	 * <p>Removes an existing course from the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course does not exist, {@code true} otherwise</p>
	 *
	 * @param courseId The ID of the course to remove
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean removeCourse(int courseId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.DELETE_COURSE.getString()
		)) {
			statement.setInt(3, courseId);
			return statement.executeUpdate() > 0;
		}

	}

	/**
	 * <p>Updates an existing course into the {@code Course} table.</p>
	 * <p>This returns {@code false} if the course was already updated, {@code true} otherwise</p>
	 *
	 * @param courseId  The ID of the course to update
	 * @param newCourse The new & updated course
	 * @return If the operation changed anything in the database
	 * @throws SQLException If an error occurs during the database update operation.
	 */
	public boolean updateCourse(
			int courseId,
			Course newCourse
	) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				Query.UPDATE_COURSE.getString()
		)) {
			statement.setString(1, newCourse.getName());
			statement.setString(2, newCourse.getDescription());
			statement.setInt(3, courseId);
			return statement.executeUpdate() > 0;
		}
	}
}
