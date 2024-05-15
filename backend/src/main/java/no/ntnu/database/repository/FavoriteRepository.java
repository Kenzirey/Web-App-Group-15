package no.ntnu.database.repository;

import no.ntnu.database.model.Favorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * An interface for SQL access to our database favorite table.
 */
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Favorite.FavoriteId> {
	Iterable<Favorite> findAllByIdUserId(long userId);
}
