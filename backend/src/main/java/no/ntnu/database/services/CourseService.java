package no.ntnu.database.services;

import java.util.Optional;
import no.ntnu.database.entities.Course;
import no.ntnu.database.repositories.CourseRepository;
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

	private final CourseRepository repository;

	/**
	 * Makes the course service.
	 *
	 * @param courseRepository The repository class for communication
	 */
	@Autowired
	public CourseService(CourseRepository courseRepository) {
		this.repository = courseRepository;
	}

	/**
	 * Adds a course to the database.
	 *
	 * @param course the {@link Course} being added to the database.
	 * @return the added {@link Course}'s id.
	 */
	public int add(Course course) {
		if (!course.isValid()) {
			LOGGER.warn("Course is invalid");
		}
		repository.save(course);
		return course.getCourseId();
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
     * Returns the count of all courses in the database.
     *
     * @return the count of courses.
     */
    public long countAllCourses() {
        return repository.count();
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
			throw new IllegalStateException(String.format("Course with ID %s not found ", id));
		} else if (!course.isValid()) {
			throw new IllegalArgumentException("Course is invalid");
		} else {
			//Before the setCourseId, JPA did not have enough info to link this new "updated object"
			//So it just added it as a new object, without deleting the old. I.e. did not update.
			course.setCourseId(id); //Important.
			repository.save(course);
		}
	}

	/**
	 * Deletes a course from the database corresponding to its ID.
	 *
	 * @param id the ID of the course to delete.
	 * @return True if the course was found and was deleted. False if not.
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
	 * @return the course with the given ID, or an empty Optional if not found.
	 */
	public Optional<Course> findById(int id) {
		//To access findById inside the course controller.
		return repository.findById(id);
	}

	/**
	 * Searches for a specific courses.
	 *
	 * @param query The search query to use when searching for courses
	 * @return Any courses that match the search query
	 */
	public Iterable<Course> searchCourse(String query) {
		return repository.searchCourse(query);
	}
}
