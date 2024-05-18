package no.ntnu.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * CourseProvider class represents a course provider entity within the application,
 * mapped to a corresponding database table via JPA annotations.
 *
 * <p>Supports Java persistence APIs requirement of a no-argument constructor.</p>
 */
@Entity
public final class CourseProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Unique ID for the course provider", example = "160195")
	private int courseProviderId;
	@Schema(description = "Name of the course provider", example = "University in Oslo")
	private String providerName;

	@Schema(description = "URL to the course provider's website", example = "https://www.ntnu.no")
	private String url;

	@OneToMany(mappedBy = "courseProvider", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	@Schema(description = "A set of course-provider links associated with this course provider.")
	private Set<CourseProviderLink> courseProviderLinks = new HashSet<>();


	/**
	 * Empty constructor for JPA requirement.
	 */
	public CourseProvider() {
		//Empty constructor for JPA.
	}

	/**
	 * Checks if the course provider is a valid provider.
	 *
	 * @return Returns true argument is valid, false otherwise.
	 */
	@JsonIgnore
	public boolean isValid() {
		boolean valid = false;
		if (courseProviderId > 0 && providerName != null && !providerName.isBlank()) {
			valid = true;
		}
		return valid;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setCourseProviderId(int courseProviderId) {
		this.courseProviderId = courseProviderId;
	}

	public int getCourseProviderId() {
		return courseProviderId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<CourseProviderLink> getCourseProviderLinks() {
		return courseProviderLinks;
	}

	public void setCourseProviderLinks(Set<CourseProviderLink> courseProviderLinks) {
		this.courseProviderLinks = courseProviderLinks;
	}

}
