package no.ntnu.database.repository;

import java.util.Optional;
import no.ntnu.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for SQL-handling of User model.
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) FROM users u WHERE u.isTwoFactorEnabled = true")
    long countByTwoFactorEnabledTrue();
}
