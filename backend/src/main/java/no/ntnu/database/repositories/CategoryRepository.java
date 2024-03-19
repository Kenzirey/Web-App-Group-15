package no.ntnu.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
	@Query("""
			SELECT c
			FROM Category c
			WHERE REPLACE(c.categoryName, ' ', '') LIKE %REPLACE(?1, ' ', '')%
			"""
	)
	Iterable<Category> searchCategory(String query);
}
