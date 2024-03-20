package no.ntnu.database.repositories;


import no.ntnu.database.entities.Favorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database image table.
 */
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Integer> {
}
