package no.ntnu.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.Favorite;

/**
 * An interface for SQL access to our database image table.
 */
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {
}
