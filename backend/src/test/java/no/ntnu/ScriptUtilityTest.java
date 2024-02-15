package no.ntnu;

import no.ntnu.database.ScriptUtility;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.Statement;

import static no.ntnu.database.ScriptUtility.getMySQLDataSourceTest;

public class ScriptUtilityTest {
  private static final String BASE_SCRIPT_PATH = "no/ntnu/projectgroup15app/database/scripts/";
  //Is there a simpler way of setting resource path?

  @Test
  public void givenConnectionObjectSQLFileThenExecuteInsertAndRemove() throws Exception {
    String scriptName = "insertCategoriesScriptBanana.sql";
    String path = Paths.get(ClassLoader.getSystemClassLoader()
            .getResource(BASE_SCRIPT_PATH + scriptName).toURI()).toString();
    //TODO: improve this test.
    //Surely the way of running a full delete script after is not a good idea.
    ScriptUtility.runScript(path, getMySQLDataSourceTest().getConnection());

    //AutoClosable, use ().
    try (
            Statement statement = getMySQLDataSourceTest().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM category");
    ) {
      if (resultSet.next()) {
        int count = resultSet.getInt(1);
        Assert.assertEquals("Incorrect number of records inserted", 2, count);
      }
    }
    String deleteScriptName = "deleteFromCategoriesScriptTest.sql";
    String deletePath = Paths.get(ClassLoader.getSystemClassLoader()
            .getResource(BASE_SCRIPT_PATH + deleteScriptName).toURI()).toString();
    ScriptUtility.runScript(deletePath, getMySQLDataSourceTest().getConnection());

  }
}
