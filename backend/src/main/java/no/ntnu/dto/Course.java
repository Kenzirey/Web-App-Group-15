package no.ntnu.dto;

/**
 * Represents a course entity (model) for tracking which admin created it.
 */
public class Course {
	private String name;
	private String description;
	private int createdBy;

	public Course() {
	}

	/**
	 * Constructs a course with the name of the admin.
	 *
	 * @param name 			the name of the course.
	 * @param description	description of the course.
	 * @param createdBy		the unique id of the admin who created the course.
	 */
	public Course(String name, String description, int createdBy) {
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
}
