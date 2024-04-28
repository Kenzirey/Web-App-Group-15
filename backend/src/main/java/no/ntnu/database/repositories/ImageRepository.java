package no.ntnu.database.repositories;

import no.ntnu.database.entities.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database image table.
 */
@Repository
public interface ImageRepository  extends CrudRepository<Image, Integer> {

}
