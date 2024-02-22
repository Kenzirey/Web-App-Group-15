package no.ntnu.database;

/**
 * Stores SQL queries used in the application.
 */
public enum Query {
	SELECT_CATEGORY_ALL("SELECT * FROM category"),
	SEARCH_FOR_CATEGORY("SELECT * FROM category WHERE categoryName LIKE ?;"),
	SELECT_COURSE_PROVIDER_ALL("SELECT * FROM courseProvider"),
	SEARCH_FOR_COURSE_PROVIDER("SELECT * FROM courseProvider WHERE courseProviderName LIKE ?;"),
	SELECT_FAVORITE_ALL("SELECT * FROM favorite"),
	SEARCH_FOR_FAVORITE("SELECT * FROM favorite WHERE favoriteName LIKE ?;"),
	// TODO: do we need image here? What to do about it.
	SEARCH_PRODUCT_ALL("SELECT * FROM product"),
	SEARCH_FOR_PRODUCT("SELECT * FROM product WHERE productName LIKE ?;"),
	SEARCH_PRODUCT_PROVIDER_ALL("SELECT * FROM productProvider"),
	//TODO: what to do about product provider, as it's a composite key.
	SEARCH_USER_ALL("SELECT * FROM user"),
	SEARCH_FOR_USER("SELECT * FROM user WHERE userName LIKE ?;"),

	INSERT_COURSE("INSERT INTO Courses (CourseName, CourseDescription, CreatedBy) VALUES (?, ?, ?);"),
	UPDATE_COURSE("UPDATE Courses SET CourseName = ?, CourseDescription = ? WHERE CourseID = ?;"),
	DELETE_COURSE("DELETE FROM Courses WHERE CourseID = ?;");



	private final String string;

	/**
	 * Constructor.
	 *
	 * @param string The string associated with the query
	 */
	Query(String string) {
		this.string = string;
	}

	/**
	 * Gets the string associated with this query.
	 *
	 * @return The string associated with this query
	 */
	public String getString() {
		return string;
	}
}
