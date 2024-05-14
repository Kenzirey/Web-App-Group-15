package no.ntnu.database.repository;

import no.ntnu.database.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database image table.
 */
@Repository
public interface ImageRepository  extends CrudRepository<Image, Integer> {
}
