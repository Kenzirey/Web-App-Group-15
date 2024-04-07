package no.ntnu.database.services;

import java.util.Optional;

import no.ntnu.database.entities.Favorite;
import no.ntnu.database.repositories.FavoriteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;



/**
 * Service class for handling business logic for favorites
 * Interacts with the {@link FavoriteRepository} to perform CRUD operations.
 */
@Service
public class FavoriteService {


	private static final Logger LOGGER = LoggerFactory.getLogger(FavoriteService.class);


	private final FavoriteRepository repository;

	/**
	 * Makes the favorite service.
	 *
	 * @param favoriteRepository The repository class for communication
	 */
	@Autowired
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.repository = favoriteRepository;
	}


	/**
	 * Adds a favorite course in the database.
	 *
	 * @param favorite The {@link Favorite} added to the database.
	 * @return The {@link Favorite}'id inserted.
	 */
	public int add(Favorite favorite) {

		if (!favorite.isValid()) {
			LOGGER.warn("Favorite is invalid");
		}

		repository.save(favorite);
		return favorite.getProductId();
	}

	/**
	 * Returns all favorites courses in the database.
	 *
	 * @return All courses in the database.
	 */
	public Iterable<Favorite> getAllFavourites() {
		return repository.findAll();
	}


	/**
	 * Returns a favorite course from the database corresponding with the ID.
	 *
	 * @param id The id of the course to return.
	 * @return The course id, or an empty Optional if not found
	 */
	public Optional<Favorite> findByProductId(int id) {
		return repository.findById(id);
	}


	/**
	 * Updates the favorite course.
	 *
	 * @param favorite The new course with a new course id
	 */
	public void updateFavorite(int id, Favorite favorite) {
		Optional<Favorite> existingFavorite = repository.findById(id);

		if (existingFavorite.isEmpty()) {
			throw new IllegalStateException(String.format("No favorite: ", id));
		} else {
			favorite.setProductId(id);
			repository.save(favorite);
		}

	}


	/**
	 *	Deletes a favorite course from the database.
	 *
	 * @return Returns true if deleted. False if the course doesn't exist in the database
	 */
	public boolean delete(int id) {
		Optional<Favorite> favorite = repository.findById(id);
		if (id < 0) {
			LOGGER.warn("Invalid ID");
		}

		if (favorite.isPresent()) {
			repository.deleteById(id);
		}
		return favorite.isPresent();
	}





}