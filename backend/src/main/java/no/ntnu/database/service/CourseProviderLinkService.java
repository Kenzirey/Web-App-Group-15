package no.ntnu.database.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import no.ntnu.database.model.Course;
import no.ntnu.database.model.CourseProvider;
import no.ntnu.database.model.CourseProviderLink;
import no.ntnu.database.model.CourseProviderLink.CourseProviderLinkId;
import no.ntnu.database.repository.CourseProviderLinkRepository;
import no.ntnu.database.repository.CourseProviderRepository;
import no.ntnu.database.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic for the link between
 * a {@link CourseProvider} and a {@link Course}.
 */
@Service
public class CourseProviderLinkService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseProviderLinkService.class);

	private final CourseProviderLinkRepository courseProviderLinkRepository;
	private final CourseRepository courseRepository;
	private final CourseProviderRepository courseProviderRepository;

	/**
	 * Creates the course provider link via autowired.
	 *
	 * @param courseProviderLinkRepository 	the repository for managing course provider links.
	 * @param courseRepository				the repository for managing courses.
	 * @param courseProviderRepository		the repository for managing course providers.
	 */
	@Autowired
	public CourseProviderLinkService(CourseProviderLinkRepository courseProviderLinkRepository,
									 CourseRepository courseRepository,
									 CourseProviderRepository courseProviderRepository) {
		this.courseProviderLinkRepository = courseProviderLinkRepository;
		this.courseRepository = courseRepository;
		this.courseProviderRepository = courseProviderRepository;
	}

	/**
	 * Adds a course listing for a specific course provider.
	 * ChatGPT v 4o helped with this specific method.
	 *
	 * @param providerId 	the ID of the {@link CourseProvider}
	 * @param dto 			the data transfer object that contains
	 *                         the {@link Course} and price information.
	 * @throws EntityNotFoundException if either the course or course provider is not found.
	 */
	public void addCourseListing(int providerId, CourseProviderLink.CourseProviderLinkDto dto) {
		CourseProviderLinkId id = new CourseProviderLinkId(dto.courseId(), providerId);
		CourseProviderLink courseProviderLink = new CourseProviderLink();
		courseProviderLink.setId(id);
		courseProviderLink.setPrice(dto.price());

		Course course = courseRepository.findById(dto.courseId())
				.orElseThrow(EntityNotFoundException::new);
		CourseProvider courseProvider = courseProviderRepository.findById(providerId)
				.orElseThrow(EntityNotFoundException::new);

		courseProviderLink.setCourse(course);
		courseProviderLink.setCourseProvider(courseProvider);

		courseProviderLinkRepository.save(courseProviderLink);
	}

	/**
	 * Deletes a course listing for a specific {@link CourseProvider}.
	 *
	 * @param providerId 	the id of the {@link CourseProvider}
	 * @param courseId		the id of the {@link Course}.
	 *
	 * @return Returns true if deleted. False if the doesn't exist.
	 */
	public boolean deleteCourseListing(int providerId, int courseId) {
		boolean success = false;
		Optional<CourseProvider> provider = courseProviderRepository.findById(providerId);
		Optional<Course> course = courseRepository.findById(courseId);

		if (provider.isPresent() && course.isPresent()) {
			CourseProviderLinkId id = new CourseProviderLinkId(courseId, providerId);
			courseProviderLinkRepository.deleteById(id);
			LOGGER.info("Listing deleted");
			success = true;
		} else {
			LOGGER.warn("No course listing found to delete for providerId: {} and courseId: {}",
					providerId, courseId);
		}
		return success;
	}

	/**
	 * Retrieves a course provider link by provider and course ID.
	 *
	 * @param providerId    the ID of the {@link CourseProvider}
	 * @param courseId      the ID of the {@link Course}
	 *
	 * @return              {@link Optional} containing the found {@link CourseProviderLink}
	 * 						or empty if not found.
	 */
	public Optional<CourseProviderLink> findCourseProviderLink(int providerId, int courseId) {
		//Create key to look for it in the repository.
		CourseProviderLinkId id = new CourseProviderLinkId(courseId, providerId);
		return courseProviderLinkRepository.findById(id);
	}

	/**
	 * Updates an existing course provider link with new pricing.
	 *
	 * @param providerId 	the ID of the {@link CourseProvider}
	 * @param courseId 		the ID of the {@link Course}
	 * @param updatedLink   the updated {@link CourseProviderLink} information
	 *
	 * @throws EntityNotFoundException if the {@link CourseProviderLink}, {@link CourseProvider},
	 * 									or {@link CourseProvider} is not found.
	 */
	public void updateCourseProviderLink(int providerId, int courseId,
										 CourseProviderLink updatedLink) {
		CourseProviderLinkId id = new CourseProviderLinkId(courseId, providerId);
		CourseProviderLink existingLink = courseProviderLinkRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Course provider link not found"));

		existingLink.setPrice(updatedLink.getPrice());

		courseProviderLinkRepository.save(existingLink);
	}
}
