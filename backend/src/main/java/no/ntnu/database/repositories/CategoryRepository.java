package no.ntnu.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.Category;


/**
 * An interface for SQL access to our database category table.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
	@Query(value = """
			SELECT c
			FROM Category c
			WHERE REPLACE(c.categoryName, ' ', '') LIKE '%' || REPLACE(:#{#query}, ' ', '') || '%'
			"""
	)
	Iterable<Category> searchCategory(@Param("query") String query);
}
