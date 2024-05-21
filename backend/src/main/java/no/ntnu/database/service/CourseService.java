package no.ntnu.database.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import no.ntnu.database.model.Course;
import no.ntnu.database.model.CourseProvider;
import no.ntnu.database.model.CourseProviderLink;
import no.ntnu.database.model.Image;
import no.ntnu.database.repository.CategoryRepository;
import no.ntnu.database.repository.CourseProviderLinkRepository;
import no.ntnu.database.repository.CourseProviderRepository;
import no.ntnu.database.repository.CourseRepository;
import no.ntnu.database.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Service class for handling business logic for courses.
 * Interacts with the {@link CourseRepository} to perform CRUD operations.
 */
@Service
public class CourseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

	private final CourseRepository repository;
	private final CategoryRepository categoryRepository;
	private final ImageRepository imageRepository;
	private final ImageService imageService;

	/**
	 * Makes the course service.
	 *
	 * @param courseRepository The repository class for communication
	 */
	@Autowired
	public CourseService(
			CourseRepository courseRepository,
			CategoryRepository categoryRepository,
			ImageRepository imageRepository,
			ImageService imageService
	) {
		this.repository = courseRepository;
		this.categoryRepository = categoryRepository;
		this.imageRepository = imageRepository;
		this.imageService = imageService;
	}

	private <T, I> Set<T> findAllAsSet(Iterable<I> ids, CrudRepository<T, I> repository) {
		Set<T> objs = new HashSet<>();
		for (T obj : repository.findAllById(ids)) {
			objs.add(obj);
		}
		return objs;
	}

	private Course makeCourseFromDto(Course.Dto courseDto) {
		return makeCourseFromDto(courseDto, new Course());
	}

	private Course makeCourseFromDto(Course.Dto courseDto, Course course) {
		course.setCourseName(courseDto.courseName());
		course.setDifficultyLevel(courseDto.difficultyLevel());
		course.setStartDate(courseDto.startDate());
		course.setEndDate(courseDto.endDate());
		course.setCourseCredits(courseDto.courseCredits());
		course.setHoursPerWeek(courseDto.hoursPerWeek());
		course.setRelatedCertification(courseDto.relatedCertification());
		course.setCourseDescription(courseDto.courseDescription());
		if (courseDto.categoryIds() != null) {
			course.setCategories(findAllAsSet(courseDto.categoryIds(), categoryRepository));
		}
		course.setImage(imageRepository.findById(courseDto.imageId()).orElse(null));
		return course;
	}

	/**
	 * Adds a course to the database.
	 *
	 * @param courseDto A {@link Course.Dto Dto} representing a
	 *                  {@link Course} to add to the database
	 * @return the added {@link Course}'s id.
	 */
	public int add(Course.Dto courseDto) {
		Course course = makeCourseFromDto(courseDto);

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
	 * @param id        The ID of the course to update.
	 * @param courseDto A {@link Course.Dto} containing updated data.
	 */
	public void updateCourse(int id, Course.Dto courseDto) {
		Course course = makeCourseFromDto(courseDto, repository.findById(id).orElseThrow(() ->
				new IllegalStateException(String.format("Course with ID %s not found ", id))
		));

		if (course.isValid()) {
			repository.save(course);
		} else {
			throw new IllegalArgumentException("Course is invalid");
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

	public int addCourseWithImage(String courseJson, MultipartFile imageFile) throws IOException {
        Course.Dto courseDto = new ObjectMapper().readValue(courseJson, Course.Dto.class);
        Image image = null;

        if (!imageFile.isEmpty()) {
            image = imageService.addImage(imageFile);
        }

        if (image != null) {
            courseDto = new Course.Dto(
                courseDto.courseName(),
                courseDto.difficultyLevel(),
                courseDto.startDate(),
                courseDto.endDate(),
                courseDto.courseCredits(),
                courseDto.hoursPerWeek(),
                courseDto.relatedCertification(),
                courseDto.courseDescription(),
                courseDto.categoryIds(),
                image.getImageId()
            );
        }

        return this.add(courseDto);
    }
}
