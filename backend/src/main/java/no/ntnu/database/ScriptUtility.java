package no.ntnu.database;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Utility for running SQL scripts.
 */
public class ScriptUtility {
  //Script Utility template taken from:
  //https://www.baeldung.com/java-run-sql-script


  //https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/executing-sql.html

  public static void runScript(String path, Connection connection) {
    //To support multiple comment delimiters.
    boolean continueOrError = false;
    boolean ignoreFailedDrops = false;
    String commentPrefix = "--";
    String separator = ";";
    String blockCommentStartDelimiter = "/*";
    String blockCommentEndDelimiter = "*/";

    ScriptUtils.executeSqlScript(
            connection,
            new EncodedResource(new PathResource(path)),
            continueOrError,
            ignoreFailedDrops,
            commentPrefix,
            separator,
            blockCommentStartDelimiter,
            blockCommentEndDelimiter
    );
  }

  /**
   * Returns a datasource to the test database in MySQL.
   *
   * @return dataSource to the test database in MySQL.
   */
  public static DataSource getMySQLDataSourceTest() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/test");
    dataSource.setUsername("root");
    dataSource.setPassword("abcd1234");

    return dataSource;
  }





}
