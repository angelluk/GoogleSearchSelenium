import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 * Represent  main search page
 */
public class Search {

	private WebDriver driver;

	/*
	 * default constructor
	 */
	public Search(WebDriver driver) {
		this.driver = driver;

		// Check that we're on the right page.
		if (!"Google".equals(driver.getTitle())) {

			throw new IllegalStateException("This is not the main Google page");
		}
	}

	/*
	 * SEARCH OBJECT PAGE LOCATORS
	 */

	// By searchQuery = By.cssSelector(".login>span>a");
	By searchQuery = By.id("lst-ib");
	By searchButton = By.cssSelector(".jsb>center>input:first-child");
	By searchNumber = By.id("resultStats");

	/*
	 * Search Queries
	 */
	public void searchQuery(String[] userQueries) throws InterruptedException {

		String resultText;
		long resultNumber;

		// Create a hash map
		//HashMap<String, Long> hm = new HashMap<String, Long>();
		TreeMap <String, Long> hm = new TreeMap <String, Long>();

		String pattern = "(\\d*)\\w+";
		String textNumber = "";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		for (String name : userQueries) {

			this.driver.get("https://www.google.com/?gws_rd=ssl#q=" + name);

			Thread.sleep(5000);

			resultText = driver.findElement(searchNumber).getText();

			System.out.println(name + " : " + resultText);

			// Now create matcher object.
			Matcher m = r.matcher(resultText);

			String tmpString = "";

			while (m.find()) {

				if (resultText.substring(m.start(), m.end()).contains(name))
					continue;

				tmpString = resultText.substring(m.start() - 4, m.end());

				if (tmpString.contains("("))
					break; // last section with time is drop

				textNumber += resultText.substring(m.start(), m.end());
			}

			resultNumber = Long.parseLong(textNumber);

			System.out.println(" RESULT of " + name + " is : " + resultNumber);
			
			// store in collection
			hm.put(name, new Long(resultNumber));

			textNumber = "";

		}

		//sort collection
		Map<?, ?> sortedMap = Search.sortByValue(hm);
		
		// show all results
		printHashMap(sortedMap);

	}

	/*
	 *  custom Comparator by value for Map collection
	 */
	public static Map sortByValue(Map unsortMap) {	 
		
		// get List from Map
		List list = new LinkedList(unsortMap.entrySet());
	 
		// provide new Comparator for list
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
							.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
	 
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	/*
	 *  method for print out Map collection
	 */
	private void printHashMap(Map<?, ?>  hm) {

		System.out.println("Print result:");
		
	
		// Get a set of the entries
		Set<?> set = hm.entrySet();

		// Get an iterator
		Iterator<Entry<String, Long>> i = (Iterator<Entry<String, Long>>) set.iterator();

		// Display elements
		while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry) i.next();
			System.out.print(me.getKey() + ": ");
			System.out.println(me.getValue());
		}
		
		System.out.println();
	}

}
