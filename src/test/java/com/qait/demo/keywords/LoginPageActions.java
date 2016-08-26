package com.qait.demo.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class LoginPageActions extends GetPage {

	WebDriver driver;

	public LoginPageActions(WebDriver driver) {
		super(driver, "Loginpage");
		this.driver = driver;
	}

	public void enterLoginCredentials(String username, String password) {
		//wait.waitForPageToLoadCompletely();
		element("txtbox_userName").sendKeys(username);
		element("txtbox_password").sendKeys(password);
		element("btn_login").click();
		logMessage("[INFO]:User submitted login form with login credentials '"
				+ username + " and " + password + "'");
	}
}
