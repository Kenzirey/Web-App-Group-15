package no.ntnu.database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a link between a course and its provider.
 * This class defines the relationship between a specific course and the provider offering it.
 * Note: This entity serves as a bridge table in a many-to-many relationship between Course and CourseProvider.
 * This entity is no longer used
 */
@Entity
public final class CourseProviderLink {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "provider_id")
	private CourseProvider courseProvider;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@JsonIgnore
	private Course course;

	@Schema(description = "URL to the course provider's website", example = "https://www.ntnu.no")
	private String url;




	/**
	 * An empty constructor for JPA requirement.
	 */
	public CourseProviderLink() {
	}

	/**
	 * Constructor for courseProviderLink.
	 *
	 * @param id             The unique identifier for the course provider link.
	 * @param course         The course associated with this link.
	 * @param courseProvider The provider offering the associated course.
	 * @param url            URL to the course provider's website.
	 */
	public CourseProviderLink(int id, Course course, CourseProvider courseProvider, String url) {
		this.id = id;
		this.course = course;
		this.courseProvider = courseProvider;
		this.url = url;
	}


	/**
	 * Returns the course linked with a provider.
	 *
	 * @return The course.
	 */
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	/**
	 * Returns the provider linked with a course.
	 *
	 * @return The course provider.
	 */
	public CourseProvider getCourseProvider() {
		return courseProvider;
	}


	/**
	 * Sets the course provider for provider link.
	 *
	 * @param courseProvider The new course provider.
	 */
	public void setCourseProvider(CourseProvider courseProvider) {
		this.courseProvider = courseProvider;
	}


	/**
	 * Returns the url of the provider.
	 *
	 * @return The course provider.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url for provider link.
	 *
	 * @param url The new rul.
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
