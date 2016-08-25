package com.qait.demo.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static com.qait.automation.utils.YamlReader.getMapValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import java.util.Map;

/**
 *
 * @author prashant.shukla
 */
public class Account_Creation_Test {

	TestSessionInitiator test;

	@BeforeClass
	@Parameters("browser")
	public void start_test_session(@Optional String browser) {
		test = new TestSessionInitiator("Account_Creation_Test", browser);
	}

	@Test
	public void Test01_Launch_Application() {
		test.launchApplication(getData("base_url"));
		test.homepage.verify_user_is_on_home_page();
		test.homepage.navigate_to_country_specific_site(getData("country.area"), getData("country.name"));
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass
	public void stop_test_session() {
		test.closeTestSession();
	}

}
