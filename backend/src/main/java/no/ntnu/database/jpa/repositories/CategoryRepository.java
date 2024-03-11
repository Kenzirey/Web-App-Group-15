package no.ntnu.database.jpa.repositories;

import no.ntnu.database.jpa.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
