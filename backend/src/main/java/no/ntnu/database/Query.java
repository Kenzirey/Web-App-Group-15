package no.ntnu.database;

/**
 * Stores SQL queries used in the application.
 */
public enum Query {
	SELECT_CATEGORY_ALL("SELECT * FROM category"),
	SEARCH_FOR_CATEGORY("SELECT * FROM category WHERE categoryName LIKE ?;");

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
