package no.ntnu.database.jwt;


import java.util.Optional;
import no.ntnu.database.entities.User;
import no.ntnu.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Provides AccessUserDetails needed for authentication.
 * Taken from 05-jwt-authentication from app-dev repository by Gist.
 */
@Service
public class AccessUserService implements UserDetailsService {

	private final UserRepository repository;

	@Autowired
	public AccessUserService(UserRepository repository) {
		this.repository = repository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUsername(username);
		if (user.isPresent()) {
			return new AccessUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
	}
}
