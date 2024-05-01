package no.ntnu.database.repositories;

import no.ntnu.database.entities.CourseProviderLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database CourseProviderLink table.
 */
@Repository
public interface CourseProviderLinkRepository extends CrudRepository<CourseProviderLink, Integer> {
}
