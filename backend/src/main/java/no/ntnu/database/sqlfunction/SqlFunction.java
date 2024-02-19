package no.ntnu.database.sqlfunction;

import java.sql.SQLException;

/**
 * Identical to {@link java.util.function.Function Function},
 * except it can throw {@link SQLException}.
 *
 * @param <T> The type of the input to the function
 * @param <R> The type of the result of the function
 */
public interface SqlFunction<T, R> {
	/**
	 * Applies this function to the given argument.
	 *
	 * @param t – The function argument
	 * @return The function result
	 * @throws SQLException If an uncaught {@link SQLException} happens in this function
	 */
	R apply(T t) throws SQLException;
}
