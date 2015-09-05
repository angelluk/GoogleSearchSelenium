

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;





public class WebDriverManager {

	private WebDriver	driver; // webdriver
		
	
	public WebDriverManager (WebDriver	driver){  
		
		this.driver = driver;
	}
	
	public static  enum Browser { 	// enum for support browsers
		CHROME,
		OPERA, 
		SAFARI, 
		IE, 
		HTMLUNIT,
		FIREFOX
	};
	
	public WebDriver getWebDriverFor(Browser brow){	// get webdriver for particular browser
		
		 switch (brow) {
         
			 case CHROME:	System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
	 						driver = new ChromeDriver();
	 						driver.manage().window().maximize();
	 						break; //WORK
	 						
	/*         case OPERA: 	driver = new OperaDriver(); //  maximize() not support yet
	         				break;
	*/                  
	         case SAFARI:	driver = new SafariDriver(); 
	         				driver.manage().window().maximize();
	                  		break;
	                  
	         case IE:		System.setProperty("webdriver.ie.driver", "D:\\Program Files\\Internet Explorer\\IEDriverServer.exe");
	 						driver = new InternetExplorerDriver();;
	 						break;
	                      
	         case HTMLUNIT:	driver = new HtmlUnitDriver(true);
	         				((HtmlUnitDriver) driver).setJavascriptEnabled(true);
	                   		break;
	                  		
	         case FIREFOX:	driver = new FirefoxDriver();
	         				driver.manage().window().maximize();
        					break; //WORK         		
		 }
	
		 return driver;
	};
	
}
