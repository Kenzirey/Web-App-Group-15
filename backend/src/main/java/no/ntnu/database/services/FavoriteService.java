package no.ntnu.database.services;

import no.ntnu.database.repositories.FavoriteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling business logic for favorites
 * Interacts with the {@link FavoriteRepository} to perform CRUD operations.
 */
@Service
public class FavoriteService {



	private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

	@Autowired
	private FavoriteRepository favoriteRepository;

	//TODO: Bli ferdig med dette
}
