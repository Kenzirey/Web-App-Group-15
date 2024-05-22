package no.ntnu.database.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
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
	private final ExchangeRateService exchangeRateService;

	/**
	 * Creates the course provider link via autowired.
	 *
	 * @param courseProviderLinkRepository The repository for managing course provider links.
	 * @param courseRepository             The repository for managing courses.
	 * @param courseProviderRepository     The repository for managing course providers.
	 * @param exchangeRateService          The service class for converting prices.
	 */
	@Autowired
	public CourseProviderLinkService(
			CourseProviderLinkRepository courseProviderLinkRepository,
			CourseRepository courseRepository,
			CourseProviderRepository courseProviderRepository,
			ExchangeRateService exchangeRateService
	) {
		this.courseProviderLinkRepository = courseProviderLinkRepository;
		this.courseRepository = courseRepository;
		this.courseProviderRepository = courseProviderRepository;
		this.exchangeRateService = exchangeRateService;
	}

	/**
	 * Converts the currency of one or more links. Conversion is done in-place.
	 *
	 * @param currency The currency to convert all links to
	 * @param links    The links to convert the currency of
	 */
	public void convertCurrencies(String currency, CourseProviderLink... links) {
		Arrays.stream(links).forEach(link -> {
			if (!link.getCurrency().equalsIgnoreCase(currency)) {
				link.setPrice(exchangeRateService.exchangeAmount(
						link.getPrice(),
						link.getCurrency(),
						currency
				));
				link.setCurrency(currency);
			}
		});
	}

	/**
	 * Adds a course listing for a specific course provider.
	 * ChatGPT v 4o helped with this specific method.
	 *
	 * @param providerId The ID of the {@link CourseProvider}
	 * @param courseId   The ID of the {@link Course}
	 * @param dto        The data transfer object that contains
	 *                   The {@link Course} and price information.
	 * @throws EntityNotFoundException if either the course or course provider is not found.
	 */
	public void addCourseListing(
			int providerId,
			int courseId,
			CourseProviderLink.CourseProviderLinkDto dto
	) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(EntityNotFoundException::new);
		CourseProvider courseProvider = courseProviderRepository.findById(providerId)
				.orElseThrow(EntityNotFoundException::new);

		courseProviderLinkRepository.save(new CourseProviderLink(
				course,
				courseProvider,
				dto.price(),
				dto.currency()
		));
	}

	/**
	 * Deletes a course listing for a specific {@link CourseProvider}.
	 *
	 * @param providerId the id of the {@link CourseProvider}
	 * @param courseId   the id of the {@link Course}.
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
	 * @param providerId The ID of the {@link CourseProvider}
	 * @param courseId   The ID of the {@link Course}
	 * @param currency   The currency to convert the {@link Course} price to, if the link is found
	 * @return {@link Optional} containing the found {@link CourseProviderLink}
	 *     or empty if not found.
	 */
	public Optional<CourseProviderLink> findCourseProviderLink(
			int providerId,
			int courseId,
			String currency
	) {
		//Create key to look for it in the repository.
		CourseProviderLinkId id = new CourseProviderLinkId(courseId, providerId);
		return courseProviderLinkRepository
				.findById(id)
				.map(link -> {
					convertCurrencies(currency, link);
					return link;
				});
	}

	/**
	 * Updates an existing course provider link with new pricing.
	 *
	 * @param providerId The ID of the {@link CourseProvider}
	 * @param courseId   The ID of the {@link Course}
	 * @param dto        the data transfer object containing price and currency information.
	 * @throws EntityNotFoundException If the {@link CourseProviderLink}, {@link CourseProvider},
	 *                                 or {@link CourseProvider} is not found.
	 */
	public void updateCourseProviderLink(
			int providerId,
			int courseId,
			CourseProviderLink.CourseProviderLinkDto dto
	) {
		CourseProviderLinkId id = new CourseProviderLinkId(courseId, providerId);
		CourseProviderLink link = courseProviderLinkRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Course provider link not found"));
		link.setPrice(dto.price());
		link.setCurrency(dto.currency());
		courseProviderLinkRepository.save(link);
	}
}
