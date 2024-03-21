package no.ntnu.database.services;

import java.util.Optional;
import no.ntnu.database.entities.CourseProvider;
import no.ntnu.database.repositories.CourseProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic for the course provider.
 * Interacts with the {@link CourseProviderRepository} to perform CRUD operations.
 */
@Service
public class CourseProviderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseProviderService.class);

	private final CourseProviderRepository repository;

	/**
	 * Creates the course service via autowired.
	 *
	 * @param repository the repository class for communication.
	 */
	@Autowired
	public CourseProviderService(CourseProviderRepository repository) {
		this.repository = repository;
	}

	/**
	 * Adds a course provider in the database.
	 *
	 * @param provider 	the {@link CourseProvider} being added to the database.
	 * @return 			the added {@link CourseProvider}'s id.
	 */
	public int add(CourseProvider provider) {
		if (!provider.isValid()) {
			LOGGER.warn("Provider is invalid");
		}
		repository.save(provider);
		return provider.getCourseProviderId();
	}

	/**
	 * Deletes a course provider from the database corresponding to its ID.
	 *
	 * @param id 	the ID of the course provider to delete.
	 * @return 		True if the course was found and was deleted. False if not.
	 */
	public boolean delete(int id) {
		Optional<CourseProvider> provider = repository.findById(id);
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}
		if (provider.isPresent()) {
			repository.deleteById(id);
		}
		return provider.isPresent();
	}

	/**
	 * Updates a course provider in the database, corresponding to its ID.
	 *
	 * @param id       the ID of the course provider to update.
	 * @param provider the updated {@link CourseProvider} data.
	 */
	public void updateCourseProvider(int id, CourseProvider provider) {
		Optional<CourseProvider> existingProvider = repository.findById(id);

		if (existingProvider.isEmpty()) {
			LOGGER.warn("Course with ID {} not found ", id);
		}
		if (!provider.isValid()) {
			LOGGER.warn("Provider is invalid");
		}
		provider.setCourseProviderId(id);
		repository.save(provider);
	}

	/**
	 * Returns a course provider from the database, corresponding to its ID.
	 *
	 * @param id 	the ID of the course provider to return.
	 * @return 		the course provider with the given ID, or an empty Optional if not found.
	 */
	public Optional<CourseProvider> findById(int id) {
		return repository.findById(id);
	}

	/**
	 * Returns all the course providers in the repository.
	 *
	 * @return all the course providers.
	 */
	public Iterable<CourseProvider> getAllProviders() {
		return repository.findAll();
	}

}
