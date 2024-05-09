package no.ntnu.database.repositories;

import no.ntnu.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

/**
 * Repository for SQL-handling of User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) FROM users u WHERE u.isTwoFactorEnabled = true")
    long countByTwoFactorEnabledTrue();
}
