package no.ntnu.database.jpa;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for handling business logic for courses.
 * Interacts with the {@link CourseRepository} to perform CRUD operations.
 */
@Service
public class CourseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

	@Autowired
	private CourseRepository repository;

	/**
	 * Adds or updates a course in the database.
	 *
	 * @param course the {@link Course} to add to, or updated in the database.
	 * @return the added or updated {@link Course}.
	 */
	public Course add(Course course) {
		if (!course.isValid()) {
			LOGGER.warn("Course is invalid");
		}
		return repository.save(course);
	}

	/**
	 * Returns all courses in the database.
	 *
	 * @return all the courses in the database.
	 */
	public Iterable<Course> getAllCourses() {
		return repository.findAll();
	}

	/**
	 * Updates a course in the database corresponding to its ID.
	 *
	 * @param id     the ID of the course to update.
	 * @param course the updated {@link Course} data.
	 */
	public void updateCourse(int id, Course course) {
		Optional<Course> existingCourse = repository.findById(id);

		if (existingCourse.isEmpty()) {
			LOGGER.warn("Course with ID {} not found", id);
		}
		if (!course.isValid()) {
			LOGGER.warn("Course is invalid");
		}
		repository.save(course);
	}

	/**
	 * Deletes a course from the database corresponding to its ID.
	 *
	 * @param id the ID of the course to delete.
	 *
	 * @return True if the course was found and got deleted. False if not.
	 */
	public boolean delete(int id) {
		Optional<Course> course = repository.findById(id);
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}
		if (course.isPresent()) {
			repository.deleteById(id);
		}
		return course.isPresent();
	}

	/**
	 * Returns a course from the database corresponding to its ID.
	 *
	 * @param id the ID of the course to return.
	 *
	 * @return the course with the given ID, or an empty Optional if not found.
	 */
	public Optional<Course> findById(int id) {
		//To access findById inside the course controller.
		return repository.findById(id);
	}
}
