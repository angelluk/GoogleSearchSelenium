import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Test_Actions {

	public static WebDriver driver;
	public String[] searchQueries;

	private final String FILE_NAME = "Query/Query.txt";

	@BeforeClass
	public static void setUp() throws Exception {

		// create class WebDriverManager and get driver for chooses browser
		driver = new WebDriverManager(driver)
				.getWebDriverFor(WebDriverManager.Browser.FIREFOX);
		// set time for wait driver
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/*
	 * test method for main actions
	 */
	@Test
	public void testUserSearch() throws Exception {

		searchQueries = readQueriesFromFile(FILE_NAME);

		// load main page
		driver.get("http://www.google.com/");

		// create Search Page Object
		Search currSrch = new Search(driver);

		// search for each query
		currSrch.searchQuery(searchQueries);

	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	private String[] readQueriesFromFile(String fileName) {

		String csvFile = fileName;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] query = {};

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				query = line.split(cvsSplitBy);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		return query;
	}

}
