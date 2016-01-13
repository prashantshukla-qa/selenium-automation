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
		//test = new TestSessionInitiator("Account_Creation_Test", browser);
	}

	@Test
	public void Test01_Launch_Application() {
		test.launchApplication(getData("base_url"));
		test.homepage.verify_user_is_on_home_page();
		test.homepage.navigate_to_country_specific_site(getData("country.area"), getData("country.name"));
	}

	@Test
	public void Test02_Navigate_To_Registration_Page() {
		test.homepage.navigate_to_account_page();
		test.accountpage.open_registration_form();
	}

	@Test
	public void Test03_Fill_Mandatory_Information_On_Registration_Form() {

		Map<String, Object> inputs = getYamlValues("registration.inputs");

		/**
		 * This fills in all the fields of the registration page
		 */
		for (int i = 1; i <= inputs.size(); i++) {

			String inputField = getMapValue(inputs, "field_" + i + ".label");
			String inputValue = getMapValue(inputs, "field_" + i + ".value");

			test.accountpage.fill_registration_rnput_rields(inputField, inputValue);
		}

		// test.accountpage.clickSignUpButton();
	}

	@Test
	public void Test04_Verify_Account_Is_Created() {

		 test.accountpage.verifyNewAccountIsCreated(getData("registration.successMessage"));
		//
		// test.accountpage.clickSkipButton();
		//
		// test.homepage.logOut();
		//
		// test.accountpage.verifyRegisrationButton();
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
