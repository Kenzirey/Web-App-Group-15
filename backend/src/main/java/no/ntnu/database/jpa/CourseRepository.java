package no.ntnu.database.jpa;

import no.ntnu.database.jpa.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer>{
}
