package no.ntnu.database;

import no.ntnu.database.model.Role;
import no.ntnu.database.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * A class which ensures necessary roles are present in the database when Spring
 * Boot app has started.
 * Code adapted from DummyData from the app-dev repository by Gist.
 */
@Component("insertRoles")
public class InsertRoles implements ApplicationListener<ApplicationReadyEvent> {

	private final RoleRepository roleRepository;

	private final Logger logger = LoggerFactory.getLogger(InsertRoles.class);

	/**
	 * Creates an insert roles component through Autowired.
	 *
	 * @param roleRepository the {@link RoleRepository} for role entity operations.
	 */
	@Autowired
	public InsertRoles(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * This method is called when the spring application boots.
	 * Checks if admin and user roles are in the database.
	 * As user & admin roles are required for user authentication.
	 *
	 * @param event Event which we don't use :).
	 */
	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		logger.info("Checking for necessary roles in database");

		boolean roleUserExists = roleRepository.findByName("ROLE_USER").isPresent();
		boolean roleAdminExists = roleRepository.findByName("ROLE_ADMIN").isPresent();

		if (!roleUserExists) {
			Role userRole = new Role("ROLE_USER");
			roleRepository.save(userRole);
			logger.info("Inserted ROLE_USER into the database.");
		}

		if (!roleAdminExists) {
			Role adminRole = new Role("ROLE_ADMIN");
			roleRepository.save(adminRole);
			logger.info("Inserted ROLE_ADMIN into the database.");
		}

		if (roleUserExists && roleAdminExists) {
			logger.info("Both ROLE_USER and ROLE_ADMIN exist in the database. No action needed.");
		}
	}
}
