package no.ntnu.database.repository;

import no.ntnu.database.model.CourseProviderLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database's course provider link.
 */
@Repository
public interface CourseProviderLinkRepository extends CrudRepository<CourseProviderLink,
											CourseProviderLink.CourseProviderLinkId> {
}
