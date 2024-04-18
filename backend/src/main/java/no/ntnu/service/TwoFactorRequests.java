package no.ntnu.service;

import java.util.Optional;
import no.ntnu.database.entities.User;
import no.ntnu.database.repositories.UserRepository;
import no.ntnu.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service for handling two-factor authentication operations.
 */
@Service("twoFactorRequests")
public class TwoFactorRequests {
  	private final UserRepository userRepository;

  	@Autowired
  	public TwoFactorRequests(UserRepository userRepository) {
		this.userRepository = userRepository;
  	}
	
	public Optional<String> get2FaSecretKey(String username) {
		return userRepository.findByUsername(username)
						 .map(User::getTwoFactorSecret);
 	}

	/**
	 * Checks if two-factor authentication is enabled for a specified user.
	 *
	 * @param username the username of the user to check
	 * @return true if 2FA is enabled, false otherwise
	 */
	public boolean get2FaEnabled(String username) {
		return userRepository.findByUsername(username)
						.map(User::isTwoFactorEnabled)
						.orElse(false);
	}


	/**
	 * Saves the two-factor authentication secret key for a specified user and enables 2FA.
	 *
	 * @param username the username of the user whose 2FA secret key is to be saved
	 * @param secretKey the new 2FA secret key to be set
	 */
	public void saveUser2FaSecretKey(String username, String secretKey) {
		userRepository.findByUsername(username).ifPresent(user -> {
			user.setTwoFactorSecret(secretKey);
			user.setTwoFactorEnabled(true);
			userRepository.save(user);
		});
	}

	/**
	 * Enables two-factor authentication for a user by generating a new secret key, saving it and
	 * updating their 2FA enabled status.
	 *
	 * @param username the username of the user for who 2FA is to be enabled
	 */
	public void enable2FaForUser(String username) {
		userRepository.findByUsername(username).ifPresent(user -> {
			if (!user.isTwoFactorEnabled()) {
				String secretKey = SecurityUtil.generate2FaSecretKey();
				user.setTwoFactorSecret(secretKey);
				user.setTwoFactorEnabled(true);
				userRepository.save(user);
			}
		});
	}
}
