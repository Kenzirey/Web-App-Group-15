package no.ntnu.database;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import no.ntnu.database.model.Role;
import no.ntnu.database.model.User;
import no.ntnu.database.repository.RoleRepository;
import no.ntnu.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A class to ensure that the necessary users exist on boot.
 * Users chuck and dave from project requirements are created here.
 * Code adapted from DummyData from the app-dev repository by Gist.
 */
@Component
@DependsOn("insertRoles") //Makes sure InsertRoles is run first.
public class InsertUsers implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final Logger logger = LoggerFactory.getLogger(InsertUsers.class);

	/**
	 * Creates an InsertUsers component through Autowired,
	 * with repositories and password encoder.
	 *
	 * @param userRepository    the {@link UserRepository} for user entity operations.
	 * @param roleRepository    the {@link RoleRepository} for role entity operations.
	 * @param passwordEncoder   the {@link PasswordEncoder} for encoding passwords.
	 */
	@Autowired
	public InsertUsers(UserRepository userRepository,
							   RoleRepository roleRepository,
							   PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Is called when Spring application starts.
	 *
	 * @param event the trigger event (start).
	 */
	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		logger.info("Checking for necessary users in the database");

		Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
		Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");

		if (userRole.isEmpty()) {
			logger.warn("ROLE_USER does not exist in the database. ");
			return;
		}
		if (adminRole.isEmpty()) {
			logger.warn("ROLE_ADMIN does not exist in the database. ");
			return;
		}

		Optional<User> userDave = userRepository.findByUsername("dave");
		if (userDave.isEmpty()) {
			User createUserDave = new User();
			createUserDave.setUsername("dave");
			createUserDave.setPassword(passwordEncoder.encode("Dangerous2024"));
			createUserDave.setRoles(new HashSet<>(Collections.singletonList(userRole.get())));
			userRepository.save(createUserDave);
			logger.info("Inserted user 'dave' into the database.");
		} else {
			logger.info("User 'dave' already exists in the database.");
		}

		Optional<User> userChuck = userRepository.findByUsername("chuck");
		if (userChuck.isEmpty()) {
			User createUserChuck = new User();
			createUserChuck.setUsername("chuck");
			createUserChuck.setPassword(passwordEncoder.encode("Nunchucks2024"));
			createUserChuck.setRoles(new HashSet<>(Collections.singletonList(adminRole.get())));
			userRepository.save(createUserChuck);
			logger.info("Inserted user 'chuck' into the database.");
		} else {
			logger.info("User 'chuck' already exists in the database.");
		}
	}
}

