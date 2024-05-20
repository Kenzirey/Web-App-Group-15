package no.ntnu.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
	@Column(name = "course_id")
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

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "A set of course-provider links associated with this course.")
	private Set<CourseProviderLink> courseProviderLinks = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "category_course",
			joinColumns = @JoinColumn(name = "course_Id"),
			inverseJoinColumns = @JoinColumn(name = "category_Id")

	)
	private Set<Category> categories = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "course")
	private Set<Favorite> favorites;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "image_id", referencedColumnName = "image_id")
	private Image image;


	/**
	 * Empty constructor for JPA requirement.
	 */
	public Course() {
		// Empty constructor for JPA requirement.
	}

	/**
	 * Checks if the object is a valid course.
	 *
	 * @return True if it is valid, false otherwise.
	 */
	@JsonIgnore
	public boolean isValid() {
		return courseName != null && !courseName.isBlank();
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
	 * Sets the number of hours per week for the course.
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<CourseProviderLink> getCourseProviderLinks() {
		return courseProviderLinks;
	}

	public void setCourseProviderLinks(Set<CourseProviderLink> courseProviderLinks) {
		this.courseProviderLinks = courseProviderLinks;
	}


	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Gets the set of favorite entries containing this course.
	 *
	 * @return The set of favorite entries containing this course
	 */
	public Set<Favorite> getFavorites() {
		return favorites;
	}

	/**
	 * Sets the set of favorite entries containing this course.
	 *
	 * @param favorites The set of favorite entries containing this course
	 */
	public void setFavorites(Set<Favorite> favorites) {
		this.favorites = favorites;
	}

	/**
	 * A DTO for {@link Course}.
	 * This does not include {@link Course#getFavorites() favorites},
	 * as creation of or changes to a course should never change its favorite status for any users
	 *
	 * @param courseName           The course's name, unchanged in {@link Course}
	 * @param difficultyLevel      The course's difficulty, unchanged in {@link Course}
	 * @param startDate            The course's start date, unchanged in {@link Course}
	 * @param endDate              The course's end date, unchanged in {@link Course}
	 * @param courseCredits        The number of credits the course is worth,
	 *                             unchanged in {@link Course}
	 * @param hoursPerWeek         The hours per week for this course, unchanged in {@link Course}
	 * @param relatedCertification The related certifications for this course,
	 *                             unchanged in {@link Course}
	 * @param courseDescription    The course's description, unchanged in {@link Course}
	 * @param categoryIds          The IDs of every category that should contain this course.
	 *                             This is mapped from {@code Set<Integer>} to
	 *                             {@code Set<}{@link Category}{@code >} in {@link Course}
	 * @param imageId              The ID of the image associated with this course.
	 *                             This is mapped from {@code int} to
	 *                             {@link Image} in {@link Course}
	 */
	public record Dto(
			String courseName,
			String difficultyLevel,
			Date startDate,
			Date endDate,
			double courseCredits,
			int hoursPerWeek,
			String relatedCertification,
			String courseDescription,
			Set<Integer> categoryIds,
			int imageId
	) {
	}
}
