package no.ntnu.database.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Role entity class, representing a security role in the system.
 * Code adapted from 05-jwt-authentication from app-dev repository by Gist.
 */
@Entity(name = "roles")
public class Role {
	@Id
	@GeneratedValue
	@Schema(description = "unique ID of the Role", example = "1245")
	private Long id;

	@Schema(description = "Name of the role", example = "Admin")
	private String name;

	@ManyToMany(mappedBy = "roles")
	@JsonBackReference
	@Schema(description = "A set of users associated with the role, "
			+ "managed via back reference from User entity", implementation = User.class)
	private Set<User> users = new LinkedHashSet<>();

	/**
	 * Empty constructor needed for JPA.
	 */
	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}