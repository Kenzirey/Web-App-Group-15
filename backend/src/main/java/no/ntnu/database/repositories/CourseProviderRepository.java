package no.ntnu.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.CourseProvider;

/**
 * Abstraction to reduce amount of boilerplate code required to implement data access for JPA
 * (and other persistence types).
 * An interface for SQL access to our database course provider table.
 */
@Repository
public interface CourseProviderRepository extends CrudRepository<CourseProvider, Integer> {
}
