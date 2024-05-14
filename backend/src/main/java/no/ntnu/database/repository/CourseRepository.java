package no.ntnu.database.repository;

import no.ntnu.database.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database course table.
 */
@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
	@Query(value = """
			SELECT c
			FROM Course c
			WHERE REPLACE(c.courseName, ' ', '') LIKE '%' || REPLACE(:#{#query}, ' ', '') || '%'
			"""
	)
	Iterable<Course> searchCourse(@Param("query") String query);



}
