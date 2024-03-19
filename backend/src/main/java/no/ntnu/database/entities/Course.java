package no.ntnu.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

/**
 * The Course class represents a course entity within the application,
 * mapped to a corresponding database table via JPA annotations.
 *
 * <p>Supports Java persistence API's requirement of a no-argument constructor.</p>
 */
@Entity
public final class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique ID for the course", example = "1")
	private int courseId;
	@Schema(description = "Name of the course", example = "SQL for Beginners")
	private String courseName;
	@Schema(description = "Difficulty level of the course", example = "Beginner")
	private String difficultyLevel;
	//Note, data starts YYYY-MM-DD.
	@Schema(description = "Start date of the course", example = "2021-09-25")
	private Date startDate;
	@Schema(description = "End date of the course", example = "2021-12-31")
	private Date endDate;
	@Schema(description = "Number of credits for the course", example = "7.5")
	private double courseCredits;
	@Schema(description = "Number of hours per week for the course", example = "10")
	private int hoursPerWeek;
	@Schema(description = "Related certification for the course", example = "SQL Certification")
	private String relatedCertification;
	@Schema(description = "Description of the course", example =
			"This course teaches the basics of SQL.")
	private String courseDescription;

	//TODO: Add many-to-many relationship with course provider?

	/**
	 * Empty constructor for JPA requirement.
	 */
	public Course() {
		// Empty constructor for JPA. checkstyle complains.
	}


	/**
	 * Checks if the object is a valid course.
	 *
	 * @return True if it is valid, false otherwise.
	 */
	@JsonIgnore
	public boolean isValid() {
		boolean valid = false;
		if (courseId > 0 && courseName != null && !courseName.isBlank()) {
			valid = true;
		}
		return valid;
	}

	/**
	 * Returns the course name.
	 *
	 * @return The course name.
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName The course name.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the difficulty level.
	 *
	 * @return The difficulty level.
	 */
	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	/**
	 * Sets the difficulty level of the course.
	 *
	 * @param difficultyLevel The difficulty level of the course.
	 */
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	/**
	 * Returns the start date of the course.
	 *
	 * @return The start date of the course.
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the course.
	 *
	 * @param startDate The start date of the course.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date of the course.
	 *
	 * @return the end date of the course.
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date of the course.
	 *
	 * @param endDate The end date of the course.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the number of credits the course is worth.
	 *
	 * @return The number of credits the course is worth.
	 */
	public double getCourseCredits() {
		return courseCredits;
	}

	/**
	 * Sets the number of credits the course is worth.
	 *
	 * @param courseCredits The number of credits the course is worth.
	 */
	public void setCourseCredits(double courseCredits) {
		this.courseCredits = courseCredits;
	}

	/**
	 * Returns the number of hours per week for the course.
	 *
	 * @return The number of hours per week for the course.
	 */
	public int getHoursPerWeek() {
		return hoursPerWeek;
	}

	/**
	 *  Sets the number of hours per week for the course.
	 *
	 * @param hoursPerWeek The number of hours per week for the course.
	 */
	public void setHoursPerWeek(int hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}

	/**
	 * Returns the related certification for the course.
	 *
	 * @return The related certification for the course.
	 */
	public String getRelatedCertification() {
		return relatedCertification;
	}

	/**
	 * Sets the related certification for the course.
	 *
	 * @param relatedCertification The related certification for the course.
	 */
	public void setRelatedCertification(String relatedCertification) {
		this.relatedCertification = relatedCertification;
	}

	/**
	 * Returns the description of the course.
	 *
	 * @return The description of the course.
	 */
	public String getCourseDescription() {
		return courseDescription;
	}

	/**
	 * Sets the description of the course.
	 *
	 * @param courseDescription The description of the course.
	 */
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	/**
	 * Returns the unique id for the course.
	 *
	 * @param courseId The unique id for the course.
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns the unique id for the course.
	 *
	 * @return The unique id for the course.
	 */
	public int getCourseId() {
		return courseId;
	}
}
