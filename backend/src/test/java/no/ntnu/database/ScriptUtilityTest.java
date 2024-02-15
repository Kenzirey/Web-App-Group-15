package no.ntnu.database;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.Statement;

import static no.ntnu.database.ScriptUtility.getMySqlDataSourceTest;

public class ScriptUtilityTest {
	private static final String BASE_SCRIPT_PATH = "no/ntnu/database/scripts/";
	//Is there a simpler way of setting resource path?

	@Test
	public void givenConnectionObjectSQLFileThenExecuteInsertAndRemove() throws Exception {
		String scriptName = "insertCategoriesScriptBanana.sql";
		URL url = ClassLoader.getSystemClassLoader()
				.getResource(BASE_SCRIPT_PATH + scriptName);
		String path = url == null ? "" : Paths.get(url.toURI()).toString();
		//TODO: improve this test.
		//Surely the way of running a full delete script after is not a good idea.
		ScriptUtility.runScript(path, getMySqlDataSourceTest().getConnection());

		//AutoClosable, use ().
		try (
				Statement statement = getMySqlDataSourceTest().getConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM category")
		) {
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				Assert.assertEquals("Incorrect number of records inserted", 2, count);
			}
		}
		String deleteScriptName = "deleteFromCategoriesScriptTest.sql";
		URL deleteURL = ClassLoader.getSystemClassLoader()
						.getResource(BASE_SCRIPT_PATH + deleteScriptName);
		String deletePath = deleteURL == null ? "" : Paths.get(deleteURL.toURI()).toString();
		ScriptUtility.runScript(deletePath, getMySqlDataSourceTest().getConnection());

	}
}
