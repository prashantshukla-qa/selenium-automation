package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.qait.automation.pojo.TopicNamePojo;
import com.qait.automation.utils.TakeScreenshot;
import com.qait.demo.keywords.HomePageActions;
import com.qait.demo.keywords.AccountPage;
import com.qait.demo.keywords.LoginPageActions;
import org.openqa.selenium.By;

public class TestSessionInitiator {

	protected WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */
	public HomePageActions homepage;
	public AccountPage accountpage;

	public TakeScreenshot takescreenshot;
	public TopicNamePojo topicNameValue;
	private String testname;

	public Random randomGenerator;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {
		homepage = new HomePageActions(driver);
		accountpage = new AccountPage(driver);
		topicNameValue = new TopicNamePojo();
	}

	/**
	 * Page object Initiation done
	 *
	 * @param testname
	 */
	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
		this.testname = testname;
	}

	public TestSessionInitiator(String testname, String browserName) {
		wdfactory = new WebDriverFactory(browserName);
		testInitiator(testname);
		this.testname = testname;

	}

	private void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		if (!_getSessionConfig().get("browser").toLowerCase().trim().equalsIgnoreCase("mobile")) {
			driver.manage().window().maximize();
		}
		driver.manage()
				.timeouts()
				.implicitlyWait(Integer.parseInt(getProperty("timeout")),
						TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver",
				"seleniumserverhost", "timeout", "driverpath", "appiumServer",
				"mobileDevice" };
		Map<String, String> config = new HashMap<>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("base_url"));
	}

	public void launchApplication(String base_url) {
		Reporter.log("\n[INFO]: The application url is :- " + base_url, true);
		//driver.manage().deleteAllCookies();
		driver.get(base_url);
                driver.navigate().to("http://www.coach.com/");
                //driver.findElement(By.xpath("/html/body/div[2]/div[1]/ul[1]/li[10]/a")).click();
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void closeBrowserSession() {
		Reporter.log("[INFO]: The Test: " + this.testname.toUpperCase() + " COMPLETED!"
				+ "\n", true);
		try {
			driver.close();
			Thread.sleep(3000);// [INFO]: this to wait before you close every
								// thing
		} catch (Exception b) {
			b.getMessage();
		}
	}

	public void closeTestSession() {
		closeBrowserSession();
	}
}
