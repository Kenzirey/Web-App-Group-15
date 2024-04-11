package no.ntnu.database.repositories;

import no.ntnu.database.entities.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database course table.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
	@Query(value = """
			SELECT *
			FROM Course c
			WHERE REPLACE(c.course_name, ' ', '') LIKE '%' || REPLACE(?1, ' ', '') || '%'
			""", nativeQuery = true
	)
	Iterable<Course> searchCourse(String query);
}
