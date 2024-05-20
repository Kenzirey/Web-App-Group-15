package no.ntnu.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * CourseProviderLink class represents the link between a course and a course provider,
 * including the price information.
 */
@Entity
public final class CourseProviderLink {
	@EmbeddedId
	private CourseProviderLinkId id;

	@JsonIgnore
	@Schema(description = "A course connected to the course provider")
	@ManyToOne
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private Course course;

	@JsonIgnore
	@Schema(description = "A course provider connected to a specific course")
	@ManyToOne
	@JoinColumn(name = "course_provider_id", insertable = false, updatable = false)
	private CourseProvider courseProvider;

	@NotNull
	@Column(name = "price")
	private double price;

	@NotNull
	@Column(name = "currency")
	private String currency;

	public CourseProviderLink() {
	}

	/**
	 * Creates a {@link CourseProviderLink} given the specific {@link Course},
	 * {@link CourseProvider} and the price.
	 *
	 * @param course         the {@link Course} connected to the {@link CourseProvider}.
	 * @param courseProvider the {@link CourseProvider} linked to a specific {@link Course}.
	 * @param price          the price for the {@link Course} from the {@link CourseProvider}
	 * @param currency       The currency that the price is in
	 */
	public CourseProviderLink(
			Course course,
			CourseProvider courseProvider,
			double price,
			String currency
	) {
		setId(new CourseProviderLinkId(course.getCourseId(), courseProvider.getCourseProviderId()));
		setCourse(course);
		setCourseProvider(courseProvider);
		setPrice(price);
		setCurrency(currency);
	}

	public CourseProviderLinkId getId() {
		return id;
	}

	public void setId(CourseProviderLinkId id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public CourseProvider getCourseProvider() {
		return courseProvider;
	}

	public void setCourseProvider(CourseProvider courseProvider) {
		this.courseProvider = courseProvider;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Composite primary key containing {@code course_id} and {@code course_provider_id}.
	 */
	@Embeddable
	public static class CourseProviderLinkId implements Serializable {
		@Column(name = "course_id")
		private int courseId;

		@Column(name = "course_provider_id")
		private int courseProviderId;

		public CourseProviderLinkId() {
		}

		public CourseProviderLinkId(int courseId, int courseProviderId) {
			this.courseId = courseId;
			this.courseProviderId = courseProviderId;
		}

		public int getCourseId() {
			return courseId;
		}

		public void setCourseId(int courseId) {
			this.courseId = courseId;
		}

		public int getCourseProviderId() {
			return courseProviderId;
		}

		public void setCourseProviderId(int courseProviderId) {
			this.courseProviderId = courseProviderId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			CourseProviderLinkId that = (CourseProviderLinkId) o;
			return courseId == that.courseId && courseProviderId == that.courseProviderId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(courseId, courseProviderId);
		}
	}

	/**
	 * A DTO for {@link CourseProvider}.
	 *
	 * @param courseId the {@link Course}'s id to connect the {@link CourseProvider} to.
	 * @param price    the price related to the specific
	 *                 {@link CourseProvider} on the specific {@link Course}.
	 */
	public record CourseProviderLinkDto(
			int courseId,
			double price
	) {
	}
}

