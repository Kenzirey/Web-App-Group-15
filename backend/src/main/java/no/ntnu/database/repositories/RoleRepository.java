package no.ntnu.database.repositories;

import no.ntnu.database.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SQL-handling of user Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
