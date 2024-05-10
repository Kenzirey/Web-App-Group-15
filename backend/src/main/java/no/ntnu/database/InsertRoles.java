package no.ntnu.database;

import no.ntnu.database.entities.Role;
import no.ntnu.database.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * A class which ensures necessary roles are present in the database when Spring
 * Boot app has started.
 * Code adapted from AppDev repository by Gist.
 */
@Component
public class InsertRoles implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private RoleRepository roleRepository;

	private final Logger logger = LoggerFactory.getLogger(InsertRoles.class);

	/**
	 * This method is called when the spring application boots.
	 * Checks if admin and user roles are in the database.
	 * As user & admin rols are required for user authentication.
	 *
	 * @param event Event which we don't use :).
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
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
