package no.ntnu.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.Course;

/**
 * An interface for SQL access to our database course table.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
}
