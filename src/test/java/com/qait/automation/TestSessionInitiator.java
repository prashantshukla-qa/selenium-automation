package com.qait.automation;

import static com.qait.automation.utils.YamlReader.setYamlFilePath;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

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
import com.qait.demo.keywords.MyAccountPage;


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
    public MyAccountPage myAccountpage;

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
        myAccountpage = new MyAccountPage(driver);
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
    
	public TestSessionInitiator() {
		wdfactory = new WebDriverFactory();
		testInitiator();
	}

    public TestSessionInitiator(String testname, String browserName) {
        wdfactory = new WebDriverFactory(browserName);
        testInitiator(testname);
        this.testname = testname;

    }

    public void testInitiator() {
		System.getProperty("user.dir");
		_configureBrowser();
		if (!System.getProperty("jiraBrowser").equals("null"))
			browser = System.getProperty("jiraBrowser");
		else
			browser = _getSessionConfig().get("browser");

//		if (browser.equalsIgnoreCase("Safari")) {
//			((JavascriptExecutor) driver).executeScript(
//					"var script = document.createElement('script');script.type = 'text/javascript';script.text='var event = document.createEvent(\"MessageEvent\");event.initMessageEvent(\"message\", false, false, {  type: \"alert\", text: \"hello\"}, window.location.origin, \"0\", window, null);window.dispatchEvent(event);window.addEventListener(\"message\", function(e) {  var e = document.createEvent(\"Events\");  e.initEvent(\"beforeload\", false, false);  var response = safari.self.tab.canLoad(e, e.data);  var responseEvent = document.createEvent(\"MessageEvent\");  responseEvent.initMessageEvent(\"message\", false, false, {    accepted: response.accepted,    response: response.value  }, window.location.origin, \"0\", window, null);  window.dispatchEvent(responseEvent);}, true);';document.head.appendChild(script);");
//		}
		setYamlFilePath();
		_initPage();

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
        // driver.manage().window().setSize(new Dimension(414, 628));
        driver.manage()
                .timeouts()
                .implicitlyWait(Integer.parseInt(getProperty("timeout")),
                        TimeUnit.SECONDS);
    }

    private Map<String, String> _getSessionConfig() {
        String[] configKeys = {"tier", "browser", "seleniumserver",
            "seleniumserverhost", "timeout", "driverpath", "appiumServer",
            "mobileDevice"};
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
        driver.manage().deleteAllCookies();
        driver.get(base_url);

    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void closeBrowserSession() {
        Reporter.log("[INFO]: The Test: " + this.testname.toUpperCase() + " COMPLETED!"
                + "\n", true);

        driver.quit();
        //	Thread.sleep(3000);// [INFO]: this to wait before you close every

    }

    public void closeTestSession() {
        closeBrowserSession();
    }
}
