package no.ntnu.database.jpa.repositories;

import no.ntnu.database.jpa.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


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
