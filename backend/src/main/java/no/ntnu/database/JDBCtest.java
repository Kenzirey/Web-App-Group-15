package no.ntnu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A template for connecting to a database, and running queries via Strings.
 * Primarily used for testing a simple SELECT query.
 */
public class JDBCtest {
  //Connection => Statement => ResultSet.

  private static final Logger LOGGER = Logger.getLogger(JDBCtest.class.getName());

  public static void main(String[] args) throws SQLException {
    //test is the name of the 'test' database.
    String url = "jdbc:mysql://localhost:3306/test";
    //When database is on server, change this from localhost.

    String userName = "root";
    String password = "abcd1234";

    //The query via String format.
    String query = "SELECT * FROM category";


    //Check if the driver import works.
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      LOGGER.log(Level.SEVERE, "Driver not found", e);
    }

    //Create a connection to the database
    // () auto closes at end of catch, when used with try.
    try (
            Connection connection = DriverManager.getConnection(
                    url, userName, password);
            Statement statement = connection.createStatement();
    ) {

      //https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html
      //table of data representing the result set from a database query.
      ResultSet result = statement.executeQuery(query);
      int entryNumber = 1;


      while (result.next()) {
        StringBuilder testDataBuilder = new StringBuilder();
        testDataBuilder.append("Entry ").append(entryNumber).append(": ");
        for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
          if (i > 1) testDataBuilder.append(", ");
          testDataBuilder.append(result.getMetaData().getColumnName(i)).append(" = ").append(result.getString(i));
        }
        System.out.println(testDataBuilder);

        //Increment entry number as we want the next entry to be 'entry + 1'.
        entryNumber++;
      }

    } catch (SQLException e) {
      LOGGER.log(Level.WARNING, "Error reading query ", e);
    }
  }
}
