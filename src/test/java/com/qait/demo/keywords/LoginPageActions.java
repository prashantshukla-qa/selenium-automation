package com.qait.demo.keywords;

import com.qait.automation.getpageobjects.GetPage;

import static com.qait.automation.utils.YamlReader.getData;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 *
 * @author prashantshukla
 */
public class LoginPageActions extends GetPage {

	public LoginPageActions(WebDriver driver) {
		super(driver, "LoginPage");
	}

	public void verify_User_Is_On_Login_Page() {
		verifyPageTitleContains();
	}

	public void login_to_the_application_as(String user) {
		String _username = getData("users." + user + ".username");
		String _password = getData("users." + user + ".password");
		logMessage("[INFO]: log into application as '" + user + "'");
		login_to_the_application_as(_username, _password);
	}

	public void login_to_the_application_as(String username, String password) {
		element("inp_username").clear();
		element("inp_username").sendKeys(username);
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		element("btn_signin").click();
		logMessage("[INFO]: log into the application using credentials '" + username + " / " + password + "'");
	}

	public void verify_Login_Error_Message_Is_Displayed(String errormsgtxt) {
		verifyElementTextContains("txt_errormsg", "fail");
	}

	public void verify_Submit_Button_Is_Enabled() {
		Assert.assertTrue(element("btn_signin").getAttribute("disabled")
				.equalsIgnoreCase("false"),
				"[ASSERT FAILED]: Submit button is inactive\n");
		logMessage("[ASSERT PASSED]: Submit button is active");
	}

	public void verify_submit_button_is_inactive() {
		Assert.assertTrue(element("btn_signin").getAttribute("disabled")
				.equalsIgnoreCase("true"),
				"[ASSERT FAILED]: Submit button is active\n");
		logMessage("[ASSERT PASSED]: Submit button is inactive");
	}

    public void open_login_form() {
        
    }
}
