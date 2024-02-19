package no.ntnu.database.sqlfunction;

import java.sql.SQLException;

/**
 * TODO: Javadoc
 * @param <T>
 */
public interface SqlConsumer<T> {
	/**
	 * TODO: Javadoc
	 *
	 * @param t
	 * @throws SQLException
	 */
	void accept(T t) throws SQLException;
}