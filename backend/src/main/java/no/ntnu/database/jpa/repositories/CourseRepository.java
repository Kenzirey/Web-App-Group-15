package no.ntnu.database.jpa.repositories;

import no.ntnu.database.jpa.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database course table.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
}
