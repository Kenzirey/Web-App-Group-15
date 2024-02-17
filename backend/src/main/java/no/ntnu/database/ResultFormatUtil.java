package no.ntnu.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for formatting result data from an SQL query.
 */
public class ResultFormatUtil {
	private ResultFormatUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * <p>Formats the result of an SQL query as a 2-dimensional array.</p>
	 * <p>It is formatted like this:
	 * <ol start="0">
	 *     <li>(Row 0)<ul>
	 *         <li>NameColumn0: valueRow0Column0</li>
	 *         <li>NameColumn1: valueRow0Column1</li>
	 *     </ul></li>
	 *     <li>(Row 1)<ul>
	 *         <li>NameColumn0: valueRow1Column0</li>
	 *         <li>NameColumn1: valueRow1Column1</li>
	 *     </ul></li>
	 * </ol>
	 * </p>
	 * <p>This operation leaves the result pointer after the last result when the operation is done.
	 * <b>Any entries before the pointer's current position will be ignored</b></p>
	 *
	 * @param result The result set to format as a 2-dimensional array
	 * @return A 2-dimensional array, containing the result
	 * @throws SQLException If a database error occurs
	 */
	public static List<Map<String, String>> formatResultAs2dArray(ResultSet result)
			throws SQLException {
		List<Map<String, String>> entries = new ArrayList<>();
		while (result.next()) {
			entries.add(formatCurrentRowAsMap(result));
		}
		return entries;
	}

	/**
	 * <p>Formats the current row of the result pointer into a map,
	 * with the column names as keys, and the values of that row as values.</p>
	 * <p>It is formatted like this:
	 * <ul>
	 *     <li>NameColumn0: valueColumn0</li>
	 * 	   <li>NameColumn1: valueColumn1</li>
	 * </ul>
	 * </p>
	 * <p>This operation does not move the result pointer</p>
	 *
	 * @param result The result set to format a row from
	 * @return The current row, as a map
	 * @throws SQLException If a database error occurs
	 */
	public static Map<String, String> formatCurrentRowAsMap(ResultSet result) throws SQLException {
		Map<String, String> entry = new HashMap<>();
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			entry.put(metaData.getColumnName(i), result.getString(i));
		}
		return entry;
	}

	/**
	 * Identical to {@link Function}, except it can throw {@link SQLException}.
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
}
