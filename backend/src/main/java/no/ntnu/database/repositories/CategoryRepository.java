package no.ntnu.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.database.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
	@Query(value = """
			SELECT *
			FROM Category c
			WHERE REPLACE(c.category_name, ' ', '') LIKE '%' || REPLACE(?1, ' ', '') || '%'
			""", nativeQuery = true
	)
	Iterable<Category> searchCategory(String query);
}
