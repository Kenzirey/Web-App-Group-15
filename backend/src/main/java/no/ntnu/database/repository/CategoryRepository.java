package no.ntnu.database.repository;

import no.ntnu.database.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface for SQL access to our database category table.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
