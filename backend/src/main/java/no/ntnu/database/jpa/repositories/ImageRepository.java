package no.ntnu.database.jpa.repositories;

import no.ntnu.database.jpa.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository  extends CrudRepository<Image, Integer> {
}
