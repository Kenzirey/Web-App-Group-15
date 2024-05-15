package no.ntnu.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


/**
 * Represents a favorite course, added by a user.
 */
@Entity
public final class Favorite {
	@EmbeddedId
	@JsonIgnore
	private FavoriteId id;

	@Schema(description = "An course whom the user has marked favorite")
	@ManyToOne
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private Course course;

	@Schema(description = "A user who marked the course as favorite")
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	/**
	 * An empty constructor for JPA requirement.
	 */
	public Favorite() {}

	/**
	 * Creates a favorite entry given a specified course & user.
	 *
	 * @param course The course to create an entry for
	 * @param user The user to create an entry for
	 */
	public Favorite(Course course, User user) {
		this.id = new FavoriteId(course.getCourseId(), user.getId());
		this.course = course;
		this.user = user;
	}

	public FavoriteId getId() {
		return id;
	}

	public Course getCourse() {
		return course;
	}

	public User getUser() {
		return user;
	}

	public void setId(FavoriteId id) {
		this.id = id;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Composite primary key containing {@code course_id} and {@code user_id}.
	 */
	@Embeddable
	public static class FavoriteId implements Serializable {
		@Column(name = "course_id")
		private int courseId;
		@Column(name = "user_id")
		private long userId;

		public FavoriteId() {}

		public FavoriteId(int courseId, long userId) {
			this.courseId = courseId;
			this.userId = userId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass())  {
				return false;
			}
			FavoriteId that = (FavoriteId) o;
			return courseId == that.courseId && userId == that.userId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(courseId, userId);
		}
	}
}
