package no.ntnu.database.services;

import java.util.Optional;
import no.ntnu.database.entities.Favorite;
import no.ntnu.database.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
 * Service class for handling business logic for favorites
 * Interacts with the {@link FavoriteRepository} to perform CRUD operations.
 */
@Service
public class FavoriteService {
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
	 * Adds a favorite entry in the database.
	 *
	 * @param favorite The {@link Favorite} entry to be added to the database.
	 */
	public void add(Favorite favorite) {
		repository.save(favorite);
	}

	/**
	 * Returns all favorite entries for the specified {@link no.ntnu.database.entities.User User}.
	 *
	 * @param userId The ID of the user to get all favorite entries for
	 * @return All courses in the database.
	 */
	public Iterable<Favorite> getAllFavourites(long userId) {
		return repository.findAllByIdUserId(userId);
	}

	/**
	 * Returns a favorite entry from the database with the corresponding
	 * {@link no.ntnu.database.entities.Course Course} and
	 * {@link no.ntnu.database.entities.User User} ID.
	 *
	 * @param courseId The course ID of the favorite entry to find
	 * @param userId The user ID of the favorite entry to find
	 * @return The favorite entry found, or an empty Optional if not found
	 */
	public Optional<Favorite> findById(int courseId, long userId) {
		return repository.findById(new Favorite.FavoriteId(courseId, userId));
	}

	/**
	 * Deletes a favorite entry from the database.
	 *
	 * @param courseId The course ID of the favorite entry to delete
	 * @param userId The user ID of the favorite entry to delete
	 * @return Returns true if deleted. False if the entry doesn't exist in the database
	 */
	public boolean delete(int courseId, long userId) {
		Optional<Favorite> favorite = repository
				.findById(new Favorite.FavoriteId(courseId, userId));
		favorite.ifPresent(repository::delete);
		return favorite.isPresent();
	}





}